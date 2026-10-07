package dev.wyeternol.core.command

abstract class WyCommand(
    val name: String,
    vararg val aliases: String
) {
    abstract fun execute(sender: WyCommandSender, args: Array<String>)
}
