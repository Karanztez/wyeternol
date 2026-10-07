package dev.wyeternol.world.generator

import dev.wyeternol.core.world.WyBlock

interface WyBlockModifier {
    fun setBlock(x: Int, y: Int, z: Int, block: WyBlock)
    fun fillHeight(bottomY: Int, topY: Int, block: WyBlock)
}

interface WyWorldGenerator {
    val name: String
    fun generateChunk(minX: Int, minZ: Int, modifier: WyBlockModifier)
}
