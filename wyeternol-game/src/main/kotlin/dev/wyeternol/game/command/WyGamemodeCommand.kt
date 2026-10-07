package dev.wyeternol.game.command

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.model.WyGameMode
import dev.wyeternol.core.model.WyPlayer
import dev.wyeternol.core.text.Text

class WyGamemodeCommand : WyCommand("gamemode", "gm") {
    override fun execute(sender: WyCommandSender, args: Array<String>) {
        val player = sender as? WyPlayer ?: run {
            sender.sendMessage(Text.error("เฉพาะผู้เล่นเท่านั้นที่สามารถเปลี่ยนโหมดเกมได้"))
            return
        }

        if (args.isEmpty()) {
            player.sendMessage(Text.warning("วิธีใช้: /gamemode <creative|survival|adventure|spectator>"))
            return
        }

        val targetMode = when (args[0].lowercase()) {
            "creative", "c", "1" -> WyGameMode.CREATIVE
            "survival", "s", "0" -> WyGameMode.SURVIVAL
            "adventure", "a", "2" -> WyGameMode.ADVENTURE
            "spectator", "sp", "3" -> WyGameMode.SPECTATOR
            else -> {
                player.sendMessage(Text.error("โหมดเกมไม่ถูกต้อง: ${args[0]}"))
                return
            }
        }

        player.gameMode = targetMode
        player.sendMessage(Text.success("เปลี่ยนโหมดเกมเป็น ${targetMode.name} เรียบร้อยแล้ว"))
    }
}
