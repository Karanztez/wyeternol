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
        val SMOOTH_STONE = WyBlock("minecraft:smooth_stone")
        val STONE_BRICKS = WyBlock("minecraft:stone_bricks")
        val CHISELED_STONE_BRICKS = WyBlock("minecraft:chiseled_stone_bricks")
        val QUARTZ_BLOCK = WyBlock("minecraft:quartz_block")
        val SMOOTH_QUARTZ = WyBlock("minecraft:smooth_quartz")
        val CHISELED_QUARTZ_BLOCK = WyBlock("minecraft:chiseled_quartz_block")
        val QUARTZ_PILLAR = WyBlock("minecraft:quartz_pillar")
        val SEA_LANTERN = WyBlock("minecraft:sea_lantern")
        val GLOWSTONE = WyBlock("minecraft:glowstone")
        val GOLD_BLOCK = WyBlock("minecraft:gold_block")
        val EMERALD_BLOCK = WyBlock("minecraft:emerald_block")
        val DIAMOND_BLOCK = WyBlock("minecraft:diamond_block")
        val OAK_LEAVES = WyBlock("minecraft:oak_leaves")
        val FLOWERING_AZALEA_LEAVES = WyBlock("minecraft:flowering_azalea_leaves")
        val AMETHYST_BLOCK = WyBlock("minecraft:amethyst_block")
        val WATER = WyBlock("minecraft:water")
    }
}
