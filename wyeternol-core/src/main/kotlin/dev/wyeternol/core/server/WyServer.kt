package dev.wyeternol.core.server

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.config.WyServerConfig
import dev.wyeternol.core.event.WyEventBus
import dev.wyeternol.core.model.WyPlayer
import dev.wyeternol.core.world.WyWorld
import net.kyori.adventure.text.Component

interface WyServer {
    val config: WyServerConfig
    val eventBus: WyEventBus
    val consoleSender: WyCommandSender
    val onlinePlayers: Collection<WyPlayer>
    val defaultWorld: WyWorld

    fun registerCommand(command: WyCommand)
    fun broadcast(message: Component)
    fun broadcast(text: String)
    fun stop()
}
