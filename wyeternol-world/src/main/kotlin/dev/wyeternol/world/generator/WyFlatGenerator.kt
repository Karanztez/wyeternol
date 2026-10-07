package dev.wyeternol.world.generator

import dev.wyeternol.core.world.WyBlock

class WyFlatGenerator : WyWorldGenerator {
    override val name: String = "FlatWorld"

    override fun generateChunk(minX: Int, minZ: Int, modifier: WyBlockModifier) {
        modifier.fillHeight(0, 1, WyBlock.BEDROCK)
        modifier.fillHeight(1, 40, WyBlock.DIRT)
        modifier.fillHeight(40, 41, WyBlock.GRASS_BLOCK)
    }
}
