package dev.wyeternol.server.adapter

import dev.wyeternol.core.command.WyCommandSender
import net.kyori.adventure.text.Component
import net.minestom.server.command.CommandSender

class MinestomWyCommandSender(
    val handle: CommandSender
) : WyCommandSender {
    override val name: String get() = "Console"
    override val isPlayer: Boolean get() = false

    override fun sendMessage(message: String) {
        handle.sendMessage(message)
    }

    override fun sendMessage(component: Component) {
        handle.sendMessage(component)
    }
}
