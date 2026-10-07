package dev.wyeternol.core.text

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

object Text {
    fun title(text: String): Component =
        Component.text(text, NamedTextColor.GOLD).decorate(TextDecoration.BOLD)

    fun info(text: String): Component =
        Component.text(text, NamedTextColor.AQUA)

    fun success(text: String): Component =
        Component.text(text, NamedTextColor.GREEN)

    fun warning(text: String): Component =
        Component.text(text, NamedTextColor.YELLOW)

    fun error(text: String): Component =
        Component.text(text, NamedTextColor.RED)

    fun muted(text: String): Component =
        Component.text(text, NamedTextColor.GRAY)
}
