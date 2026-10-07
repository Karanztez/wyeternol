---
name: wyeternol-micro-instance
description: Architecture and implementation workflows for micro-sized, horizontally scalable Minestom/WyEternol instances, ephemeral dungeon scaling, and memory-optimized MMO game servers.
---

# WyEternol Micro-Instance & Scaling Engineering Skill

This skill governs the design, implementation, and optimization of ultra-lightweight game instances and horizontal scaling within the **WyEternol** ecosystem.

## 🎯 Core Directive
1. **Never scale a monolithic world.** Scale lightweight, isolated, disposable instances.
2. **Stateless World, Stateful Player**: Do not write dungeon blocks to disk. Save only player character data (Stats, Level, Items, Quests) to an external persistence store (Redis/Database).
3. **Clean Architecture Isolation**: Keep all dungeon rules, timers, and party logic inside `wyeternol-core` and `wyeternol-game`. Minestom `InstanceContainer` bindings remain strictly encapsulated within `wyeternol-server`.

---

## 📐 Memory Optimization Checklist

### 1. View Distance & Chunk Loading
* Set instance view distance to **4–6 chunks** for instanced dungeons. Dungeons are enclosed; players do not need 16-chunk render distances.
* Restrict chunk generation strictly to the dungeon perimeter (`minX..maxX`, `minZ..maxZ`). Discard out-of-bounds chunks immediately.

### 2. Lighting Strategy
* **Lobby / Scenic Hubs**: Use `LightingChunk` (`setChunkSupplier { inst, cx, cz -> LightingChunk(inst, cx, cz) }`) for realistic sky and block lighting.
* **Underground Raids / Fast Dungeons**: Where dynamic sky light is unneeded, evaluate standard chunks or ambient light packet blocks to save light calculation CPU cycles.

### 3. Entity Strategy (Zero-Pathfinding Load)
* Prefer **Display Entities** (`TextDisplay`, `ItemDisplay`, `BlockDisplay`) and `Interaction` entities for bosses, holograms, damage popups, and visual skills.
* Avoid spawning hundreds of Vanilla `LivingEntity` mobs that calculate vanilla AI pathfinding unless custom behavioral trees are active.

---

## 🔄 Ephemeral Dungeon Lifecycle Pattern

Always adhere to this four-stage lifecycle when creating instanced rooms:

```
[1. ALLOCATE]       -> Create in-memory instance container (< 2ms)
[2. POPULATE]       -> Run procedural math generator in RAM (No disk write)
[3. PLAY & TICK]    -> Player party enters, event hooks active
[4. DISPOSE & GC]   -> Party exits -> Evacuate players -> unregisterInstance() -> GC
```

### Destruction Code Contract (Example):
```kotlin
fun destroyDungeon(instance: InstanceContainer, fallbackSpawn: Pos) {
    // 1. Evacuate all players to lobby or town
    instance.players.forEach { player ->
        player.teleport(fallbackSpawn)
    }
    
    // 2. Remove all entities and cancel active instance timers
    instance.entities.forEach { it.remove() }
    
    // 3. Unregister from Minestom InstanceManager to allow JVM GC
    MinecraftServer.getInstanceManager().unregisterInstance(instance)
}
```

---

## ⚙️ Recommended Production JVM Execution Flags

For optimal micro-instance performance on low-RAM Linux VPS:

```bash
java -Xms128M -Xmx1G \
     -XX:+UseG1GC \
     -XX:G1HeapRegionSize=4M \
     -XX:+ParallelRefProcEnabled \
     -XX:MaxGCPauseMillis=20 \
     -jar build/wyeternol-server.jar
```
* **`-Xms128M`**: Prevents JVM from allocating unused memory up-front.
* **`-XX:+UseG1GC` & `-XX:MaxGCPauseMillis=20`**: Ensures smooth 20 TPS with sub-20ms garbage collection pauses.
