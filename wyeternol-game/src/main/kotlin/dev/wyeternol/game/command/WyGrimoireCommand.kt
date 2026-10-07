package dev.wyeternol.game.command

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.model.WyPlayer
import dev.wyeternol.core.text.Text
import dev.wyeternol.game.gui.WyGrimoireGui

class WyGrimoireCommand : WyCommand("grimoire", "book", "quest", "gui") {
    override fun execute(sender: WyCommandSender, args: Array<String>) {
        val player = sender as? WyPlayer
        if (player == null) {
            sender.sendMessage(Text.error("คำสั่งนี้ใช้ได้เฉพาะผู้เล่นในเกมเท่านั้น!"))
            return
        }

        player.playSound("minecraft:block.enchantment_table.use", 1.0f, 1.2f)
        player.sendMessage(Text.info("📖 กำลังเปิด Celestial Grimoire..."))
        player.openGui(WyGrimoireGui())
    }
}
