package dev.wyeternol.server.adapter

import dev.wyeternol.core.world.WyWorld
import net.minestom.server.instance.InstanceContainer

class MinestomWyWorld(
    val handle: InstanceContainer,
    override val id: String = handle.uuid.toString(),
    override val name: String = "default"
) : WyWorld
