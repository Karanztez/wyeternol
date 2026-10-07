package dev.wyeternol.server.bootstrap

import dev.wyeternol.core.config.WyServerConfig
import dev.wyeternol.core.event.WyEventBus
import dev.wyeternol.core.event.WyPlayerChatEvent
import dev.wyeternol.core.event.WyPlayerJoinEvent
import dev.wyeternol.core.event.WyPlayerQuitEvent
import dev.wyeternol.core.model.WyGameMode
import dev.wyeternol.core.world.WyBlock
import dev.wyeternol.game.WyGameModule
import dev.wyeternol.server.adapter.MinestomWyServer
import dev.wyeternol.server.adapter.MinestomWyWorld
import dev.wyeternol.world.generator.WyBlockModifier
import dev.wyeternol.world.generator.WyFlatGenerator
import net.minestom.server.Auth
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerChatEvent
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.instance.block.Block
import org.slf4j.LoggerFactory
import java.util.Scanner

class WyServerBootstrap(
    private val config: WyServerConfig = WyServerConfig(port = 25598)
) {
    private val logger = LoggerFactory.getLogger(WyServerBootstrap::class.java)

    fun start() {
        logger.info("====================================================")
        logger.info("   Starting {} on port {}", config.serverName, config.port)
        logger.info("====================================================")

        // 1. Auth Setup (Default: Offline to allow direct client connects on port 25598)
        val auth: Auth = if (config.onlineMode) {
            logger.info("[Auth] Mojang Online Mode: ENABLED")
            Auth.Online()
        } else {
            logger.info("[Auth] Mojang Online Mode: DISABLED (Direct client connection enabled)")
            Auth.Offline()
        }

        // 2. Initialize Engine
        val engine = MinecraftServer.init(auth)
        val eventBus = WyEventBus()

        // 3. Initialize Default World & Procedural Generator
        val instanceManager = MinecraftServer.getInstanceManager()
        val defaultInstance = instanceManager.createInstanceContainer()
        val flatGenerator = WyFlatGenerator()

        defaultInstance.setGenerator { unit ->
            flatGenerator.generateChunk(
                unit.absoluteStart().blockX(),
                unit.absoluteStart().blockZ(),
                object : WyBlockModifier {
                    override fun setBlock(x: Int, y: Int, z: Int, block: WyBlock) {
                        unit.modifier().setBlock(x, y, z, resolveBlock(block))
                    }

                    override fun fillHeight(bottomY: Int, topY: Int, block: WyBlock) {
                        unit.modifier().fillHeight(bottomY, topY, resolveBlock(block))
                    }
                }
            )
        }

        val wyWorld = MinestomWyWorld(defaultInstance)
        val wyServer = MinestomWyServer(config, eventBus, wyWorld)

        // 4. Bridge Engine Events to WyEternol Domain Events
        val globalEventHandler = MinecraftServer.getGlobalEventHandler()

        globalEventHandler.addListener(AsyncPlayerConfigurationEvent::class.java) { event ->
            event.spawningInstance = defaultInstance
            event.player.respawnPoint = Pos(config.spawnPosition.x, config.spawnPosition.y, config.spawnPosition.z)
        }

        globalEventHandler.addListener(PlayerSpawnEvent::class.java) { event ->
            val wyPlayer = wyServer.getOrCreatePlayer(event.player)
            wyPlayer.gameMode = WyGameMode.CREATIVE
            wyPlayer.permissionLevel = 4
            eventBus.post(WyPlayerJoinEvent(wyPlayer))
        }

        globalEventHandler.addListener(PlayerDisconnectEvent::class.java) { event ->
            val wyPlayer = wyServer.getOrCreatePlayer(event.player)
            eventBus.post(WyPlayerQuitEvent(wyPlayer))
            wyServer.removePlayer(event.player)
        }

        globalEventHandler.addListener(PlayerChatEvent::class.java) { event ->
            val wyPlayer = wyServer.getOrCreatePlayer(event.player)
            val chatEvent = eventBus.post(
                WyPlayerChatEvent(
                    player = wyPlayer,
                    rawMessage = event.rawMessage,
                    formattedMessage = event.formattedMessage
                )
            )

            if (chatEvent.isCancelled) {
                event.isCancelled = true
            } else {
                event.formattedMessage = chatEvent.formattedMessage
            }
        }

        // 5. Initialize Game Systems & Commands
        val gameModule = WyGameModule(wyServer)
        gameModule.initialize()

        // 6. Console Reader Thread
        val consoleReader = Thread {
            val scanner = Scanner(System.`in`)
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine().trim()
                if (line.isEmpty()) continue
                if (line.equals("stop", ignoreCase = true) || line.equals("exit", ignoreCase = true)) {
                    logger.info("Console commanded stop. Shutting down...")
                    wyServer.stop()
                    break
                } else {
                    MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().consoleSender, line)
                }
            }
        }
        consoleReader.isDaemon = true
        consoleReader.name = "WyConsole"
        consoleReader.start()

        // 7. JVM Shutdown Hook
        Runtime.getRuntime().addShutdownHook(Thread {
            wyServer.stop()
        })

        // 8. Start Engine Listener on port
        logger.info("WyEternol Server is actively listening on {}:{}", config.host, config.port)
        engine.start(config.host, config.port)
    }

    private fun resolveBlock(block: WyBlock): Block {
        return when (block.id) {
            "minecraft:bedrock" -> Block.BEDROCK
            "minecraft:dirt" -> Block.DIRT
            "minecraft:grass_block" -> Block.GRASS_BLOCK
            "minecraft:stone" -> Block.STONE
            else -> Block.STONE
        }
    }
}
