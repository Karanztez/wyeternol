package dev.wyeternol.core.model

import dev.wyeternol.core.command.WyCommandSender
import java.util.UUID

interface WyPlayer : WyCommandSender {
    val uuid: UUID
    val username: String
    override val name: String get() = username
    override val isPlayer: Boolean get() = true

    var position: WyPosition
    var gameMode: WyGameMode
    var permissionLevel: Int

    fun teleport(target: WyPosition)
    fun openGui(gui: dev.wyeternol.core.gui.WyGui)
    fun closeGui()
    fun playSound(soundKey: String, volume: Float = 1.0f, pitch: Float = 1.0f)
}
