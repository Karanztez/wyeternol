package dev.wyeternol.game.command

import dev.wyeternol.core.command.WyCommand
import dev.wyeternol.core.command.WyCommandSender
import dev.wyeternol.core.server.WyServer
import dev.wyeternol.core.text.Text

class WyMemoryCommand(private val server: WyServer) : WyCommand("memory", "ram", "status", "mem") {
    override fun execute(sender: WyCommandSender, args: Array<String>) {
        val runtime = Runtime.getRuntime()
        val mb = 1024 * 1024

        val totalMem = runtime.totalMemory() / mb
        val freeMem = runtime.freeMemory() / mb
        val usedMem = totalMem - freeMem
        val maxMem = runtime.maxMemory() / mb
        val cores = runtime.availableProcessors()
        val players = server.onlinePlayers.size

        sender.sendMessage(Text.info("§b§l=== [WyEternol Server Performance & Memory] ==="))
        sender.sendMessage(Text.plain(" §7• §fRAM ใช้งานอยู่ (Used): §a$usedMem MB §7/ §e$totalMem MB"))
        sender.sendMessage(Text.plain(" §7• §fRAM ว่าง (Free): §b$freeMem MB"))
        sender.sendMessage(Text.plain(" §7• §fRAM สูงสุด (Max): §6$maxMem MB"))
        sender.sendMessage(Text.plain(" §7• §fCPU Cores: §e$cores คอร์"))
        sender.sendMessage(Text.plain(" §7• §fผู้เล่นออนไลน์: §a$players คน"))
        sender.sendMessage(Text.info("§b§l==============================================="))
    }
}
