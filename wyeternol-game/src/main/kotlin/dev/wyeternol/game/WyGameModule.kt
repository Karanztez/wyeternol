package dev.wyeternol.game

import dev.wyeternol.core.server.WyServer
import dev.wyeternol.game.command.WyGamemodeCommand
import dev.wyeternol.game.command.WyStopCommand
import dev.wyeternol.game.listener.WyPlayerLifecycleListener
import org.slf4j.LoggerFactory

class WyGameModule(private val server: WyServer) {
    private val logger = LoggerFactory.getLogger(WyGameModule::class.java)

    fun initialize() {
        logger.info("[WyGameModule] Initializing game features & commands...")

        // Register Commands
        server.registerCommand(WyStopCommand(server))
        server.registerCommand(WyGamemodeCommand())

        // Register Event Listeners
        WyPlayerLifecycleListener(server).register(server.eventBus)

        logger.info("[WyGameModule] Game systems ready.")
    }
}
