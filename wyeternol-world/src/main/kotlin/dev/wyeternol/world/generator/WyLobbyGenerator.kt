package dev.wyeternol.world.generator

import dev.wyeternol.core.world.WyBlock
import kotlin.math.abs
import kotlin.math.sqrt

class WyLobbyGenerator : WyWorldGenerator {
    override val name: String = "WyEternolCelestialLobby"

    override fun generateChunk(minX: Int, minZ: Int, modifier: WyBlockModifier) {
        // Platform is located at (-24..24, -24..24) around center (0, 0)
        // Only generate for chunks within range of the floating island
        if (minX < -32 || minX > 16 || minZ < -32 || minZ > 16) {
            return
        }

        val floorY = 60
        val islandBottomY = 44
        val maxRadius = 16.0

        for (x in minX until minX + 16) {
            for (z in minZ until minZ + 16) {
                val dist = sqrt((x * x + z * z).toDouble())

                // 1. Inverted floating island cone underneath (y: 44..59)
                for (y in islandBottomY until floorY) {
                    val progress = (y - islandBottomY).toDouble() / (floorY - islandBottomY)
                    // Non-linear curve for organic floating island look
                    val layerRadius = (progress * progress) * maxRadius
                    if (dist <= layerRadius) {
                        val block = when {
                            y == floorY - 1 -> WyBlock.DIRT
                            y < islandBottomY + 3 -> WyBlock.BEDROCK
                            (x * 7 + z * 13 + y * 3) % 11 == 0 -> WyBlock.AMETHYST_BLOCK
                            (x + z + y) % 9 == 0 -> WyBlock.GLOWSTONE
                            else -> WyBlock.STONE
                        }
                        modifier.setBlock(x, y, z, block)
                    }
                }

                // 2. Main Floor & Biome Ground at y = 60
                if (dist <= maxRadius) {
                    val floorBlock = when {
                        // Central Spawn Star
                        dist <= 1.5 -> WyBlock.SEA_LANTERN
                        dist <= 2.5 -> WyBlock.GOLD_BLOCK

                        // Inner Marble Promenade
                        dist <= 5.5 -> if ((abs(x) + abs(z)) % 2 == 0) WyBlock.SMOOTH_QUARTZ else WyBlock.CHISELED_QUARTZ_BLOCK

                        // Lush Sunny Grass Garden Ring
                        dist <= 9.5 -> WyBlock.GRASS_BLOCK

                        // Outer Quartz Ring & Stone Bricks
                        dist <= 12.5 -> WyBlock.SMOOTH_QUARTZ
                        dist <= 14.5 -> WyBlock.STONE_BRICKS

                        // Perimeter Glowing Rim
                        else -> WyBlock.SEA_LANTERN
                    }
                    modifier.setBlock(x, floorY, z, floorBlock)

                    // 3. Garden Decor on Grass Ring (y: 61..62)
                    if (dist > 6.5 && dist <= 8.5) {
                        // Planters / Flowering bushes at cardinal angles
                        if (abs(x) == abs(z)) {
                            modifier.setBlock(x, floorY + 1, z, WyBlock.FLOWERING_AZALEA_LEAVES)
                        } else if ((abs(x) == 7 && z == 0) || (abs(z) == 7 && x == 0)) {
                            // Garden Sea Lanterns
                            modifier.setBlock(x, floorY + 1, z, WyBlock.SEA_LANTERN)
                            modifier.setBlock(x, floorY + 2, z, WyBlock.OAK_LEAVES)
                        }
                    }

                    // 4. Outer Balustrade (y = 61)
                    if (dist > 14.5 && dist <= maxRadius) {
                        modifier.setBlock(x, floorY + 1, z, WyBlock.SMOOTH_STONE)
                    }
                }

                // 5. Four Grand Celestial Pillars at (±8, ±8)
                val isPillar = (abs(x) == 8 && abs(z) == 8)
                if (isPillar) {
                    // Pillar Base
                    modifier.setBlock(x, floorY + 1, z, WyBlock.CHISELED_QUARTZ_BLOCK)
                    // Pillar Shaft
                    for (py in (floorY + 2)..(floorY + 7)) {
                        modifier.setBlock(x, py, z, WyBlock.QUARTZ_PILLAR)
                    }
                    // Pillar Capital & Glowing Beacon
                    modifier.setBlock(x, floorY + 8, z, WyBlock.CHISELED_QUARTZ_BLOCK)
                    modifier.setBlock(x, floorY + 9, z, WyBlock.SEA_LANTERN)
                    modifier.setBlock(x, floorY + 10, z, WyBlock.GOLD_BLOCK)
                }

                // 6. Cardinal Portal / Lamp Pillars at (±12, 0) and (0, ±12)
                val isCardinalPillar = ((abs(x) == 12 && z == 0) || (abs(z) == 12 && x == 0))
                if (isCardinalPillar) {
                    modifier.setBlock(x, floorY + 1, z, WyBlock.CHISELED_STONE_BRICKS)
                    modifier.setBlock(x, floorY + 2, z, WyBlock.QUARTZ_PILLAR)
                    modifier.setBlock(x, floorY + 3, z, WyBlock.QUARTZ_PILLAR)
                    modifier.setBlock(x, floorY + 4, z, WyBlock.GLOWSTONE)
                }
            }
        }
    }
}
