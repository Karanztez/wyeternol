package dev.wyeternol.core.world

data class WyBlock(
    val id: String
) {
    companion object {
        val AIR = WyBlock("minecraft:air")
        val BEDROCK = WyBlock("minecraft:bedrock")
        val DIRT = WyBlock("minecraft:dirt")
        val GRASS_BLOCK = WyBlock("minecraft:grass_block")
        val STONE = WyBlock("minecraft:stone")
    }
}
