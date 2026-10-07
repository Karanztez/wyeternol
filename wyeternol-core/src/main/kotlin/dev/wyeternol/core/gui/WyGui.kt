package dev.wyeternol.core.gui

import dev.wyeternol.core.model.WyPlayer

data class WyGuiItem(
    val materialId: String,
    val name: String,
    val lore: List<String> = emptyList(),
    val glowing: Boolean = false,
    val amount: Int = 1,
    val onClick: ((WyPlayer) -> Unit)? = null
)

interface WyGui {
    val title: String
    val rows: Int
    val items: Map<Int, WyGuiItem>
}
