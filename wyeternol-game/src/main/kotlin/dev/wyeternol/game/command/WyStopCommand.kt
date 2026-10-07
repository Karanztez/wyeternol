package dev.wyeternol.game.command

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.server.WyServer
import dev.wyeternol.core.text.Text

class WyStopCommand(private val server: WyServer) : WyCommand("stop", "end") {
    override fun execute(sender: WyCommandSender, args: Array<String>) {
        sender.sendMessage(Text.error("กำลังสั่งปิดเซิร์ฟเวอร์ WyEternol..."))
        server.stop()
    }
}
