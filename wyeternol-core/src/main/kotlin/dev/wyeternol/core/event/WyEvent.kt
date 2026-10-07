package dev.wyeternol.core.event

import dev.wyeternol.core.model.WyPlayer
import net.kyori.adventure.text.Component

interface WyEvent

interface WyCancellableEvent : WyEvent {
    var isCancelled: Boolean
}

data class WyPlayerJoinEvent(
    val player: WyPlayer
) : WyEvent

data class WyPlayerQuitEvent(
    val player: WyPlayer
) : WyEvent

data class WyPlayerChatEvent(
    val player: WyPlayer,
    val rawMessage: String,
    var formattedMessage: Component,
    override var isCancelled: Boolean = false
) : WyCancellableEvent
