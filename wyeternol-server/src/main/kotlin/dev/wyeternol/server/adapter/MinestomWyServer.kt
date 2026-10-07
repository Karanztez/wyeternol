package dev.wyeternol.server.adapter

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.config.WyServerConfig
import dev.wyeternol.core.event.WyEventBus
import dev.wyeternol.core.model.WyPlayer
import dev.wyeternol.core.server.WyServer
import dev.wyeternol.core.world.WyWorld
import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player

class MinestomWyServer(
    override val config: WyServerConfig,
    override val eventBus: WyEventBus,
    override val defaultWorld: WyWorld
) : WyServer {

    private val playerMap = mutableMapOf<Player, MinestomWyPlayer>()

    fun getOrCreatePlayer(player: Player): MinestomWyPlayer {
        return playerMap.getOrPut(player) { MinestomWyPlayer(player) }
    }

    fun removePlayer(player: Player) {
        playerMap.remove(player)
    }

    override val consoleSender: WyCommandSender =
        MinestomWyCommandSender(MinecraftServer.getCommandManager().consoleSender)

    override val onlinePlayers: Collection<WyPlayer>
        get() = MinecraftServer.getConnectionManager().onlinePlayers.map { getOrCreatePlayer(it) }

    override fun registerCommand(command: WyCommand) {
        val minestomCmd = Command(command.name, *command.aliases)
        val args = ArgumentType.StringArray("args")

        minestomCmd.setDefaultExecutor { sender, _ ->
            val wySender: WyCommandSender = if (sender is Player) getOrCreatePlayer(sender) else consoleSender
            command.execute(wySender, emptyArray())
        }

        minestomCmd.addSyntax({ sender, context ->
            val wySender: WyCommandSender = if (sender is Player) getOrCreatePlayer(sender) else consoleSender
            val parsedArgs: Array<String> = context.get(args)
            command.execute(wySender, parsedArgs)
        }, args)

        MinecraftServer.getCommandManager().register(minestomCmd)
    }

    override fun broadcast(message: Component) {
        MinecraftServer.getConnectionManager().onlinePlayers.forEach { it.sendMessage(message) }
    }

    override fun broadcast(text: String) {
        MinecraftServer.getConnectionManager().onlinePlayers.forEach { it.sendMessage(text) }
    }

    override fun stop() {
        MinecraftServer.stopCleanly()
    }
}
