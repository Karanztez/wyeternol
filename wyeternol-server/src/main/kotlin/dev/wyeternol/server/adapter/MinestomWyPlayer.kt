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

    var currentGui: dev.wyeternol.core.gui.WyGui? = null

    override fun openGui(gui: dev.wyeternol.core.gui.WyGui) {
        val invType = when (gui.rows) {
            1 -> net.minestom.server.inventory.InventoryType.CHEST_1_ROW
            2 -> net.minestom.server.inventory.InventoryType.CHEST_2_ROW
            3 -> net.minestom.server.inventory.InventoryType.CHEST_3_ROW
            4 -> net.minestom.server.inventory.InventoryType.CHEST_4_ROW
            5 -> net.minestom.server.inventory.InventoryType.CHEST_5_ROW
            else -> net.minestom.server.inventory.InventoryType.CHEST_6_ROW
        }

        val inventory = net.minestom.server.inventory.Inventory(invType, Component.text(gui.title))

        gui.items.forEach { (slot, item) ->
            val material = net.minestom.server.item.Material.fromKey(item.materialId)
                ?: net.minestom.server.item.Material.STONE
            var builder = net.minestom.server.item.ItemStack.builder(material)
                .amount(item.amount)
                .customName(Component.text(item.name))

            if (item.lore.isNotEmpty()) {
                builder = builder.lore(item.lore.map { Component.text(it) })
            }
            if (item.glowing) {
                builder = builder.glowing(true)
            }
            inventory.setItemStack(slot, builder.build())
        }

        currentGui = gui
        handle.openInventory(inventory)
    }

    override fun closeGui() {
        currentGui = null
        handle.closeInventory()
    }

    override fun playSound(soundKey: String, volume: Float, pitch: Float) {
        handle.playSound(
            net.kyori.adventure.sound.Sound.sound(
                net.kyori.adventure.key.Key.key(soundKey),
                net.kyori.adventure.sound.Sound.Source.PLAYER,
                volume,
                pitch
            )
        )
    }
}
