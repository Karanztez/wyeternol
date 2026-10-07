package dev.wyeternol.core.command

import net.kyori.adventure.text.Component

interface WyCommandSender {
    val name: String
    val isPlayer: Boolean
    fun sendMessage(message: String)
    fun sendMessage(component: Component)
}
