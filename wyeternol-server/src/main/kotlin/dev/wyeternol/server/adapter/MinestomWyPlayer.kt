package dev.wyeternol.server.adapter

import dev.wyeternol.core.model.WyGameMode
import dev.wyeternol.core.model.WyPlayer
import dev.wyeternol.core.model.WyPosition
import net.kyori.adventure.text.Component
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.Player
import java.util.UUID

class MinestomWyPlayer(
    val handle: Player
) : WyPlayer {
    override val uuid: UUID get() = handle.uuid
    override val username: String get() = handle.username

    override var position: WyPosition
        get() = WyPosition(
            x = handle.position.x,
            y = handle.position.y,
            z = handle.position.z,
            yaw = handle.position.yaw,
            pitch = handle.position.pitch
        )
        set(value) {
            handle.teleport(Pos(value.x, value.y, value.z, value.yaw, value.pitch))
        }

    override var gameMode: WyGameMode
        get() = when (handle.gameMode) {
            GameMode.SURVIVAL -> WyGameMode.SURVIVAL
            GameMode.CREATIVE -> WyGameMode.CREATIVE
            GameMode.ADVENTURE -> WyGameMode.ADVENTURE
            GameMode.SPECTATOR -> WyGameMode.SPECTATOR
        }
        set(value) {
            handle.gameMode = when (value) {
                WyGameMode.SURVIVAL -> GameMode.SURVIVAL
                WyGameMode.CREATIVE -> GameMode.CREATIVE
                WyGameMode.ADVENTURE -> GameMode.ADVENTURE
                WyGameMode.SPECTATOR -> GameMode.SPECTATOR
            }
        }

    override var permissionLevel: Int
        get() = handle.permissionLevel
        set(value) {
            handle.permissionLevel = value
        }

    override fun teleport(target: WyPosition) {
        handle.teleport(Pos(target.x, target.y, target.z, target.yaw, target.pitch))
    }

    override fun sendMessage(message: String) {
        handle.sendMessage(message)
    }

    override fun sendMessage(component: Component) {
        handle.sendMessage(component)
    }
}
