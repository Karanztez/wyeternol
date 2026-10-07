# 🏗️ WyEternol: Micro-Instance Architecture & Scaling Manifesto

> **Philosophy**: *"Never scale a monolith. Scale ephemeral micro-instances."*  
> Keep the base server footprint ultra-light (~70-150 MB RAM), and scale game worlds horizontally as lightweight, disposable instances.

---

## 1. The Monolith Problem vs. The Micro-Instance Paradigm

| Dimension | 🧱 Traditional Paper/Vanilla Monolith | ⚡ WyEternol Micro-Instance Engine |
| :--- | :--- | :--- |
| **World Model** | All dimensions tick in a single shared main-thread world loop. | Every dungeon/room is an isolated `InstanceContainer`. |
| **Memory per World** | 1.5 GB – 4.0 GB (Full region files, entity registries, light maps). | **100 KB – 5 MB** (Pure procedural blocks, no file I/O). |
| **Instance Lifecycle** | Static (server restart required to load/unload worlds safely). | **Ephemeral**: Created in milliseconds, destroyed when party leaves. |
| **Tick Isolation** | One player lagging ticks drops TPS for the entire server. | Each instance ticks independently; zero cross-instance lag. |
| **State Persistence** | Saves entire chunk blocks and tile entities to disk. | **Stateless**: World is discarded; only Player Profile is saved. |

---

## 2. Architectural Blueprint

```
                     ┌───────────────────────────┐
                     │   Minecraft Clients (26.2) │
                     └─────────────┬─────────────┘
                                   │
                                   ▼
             ┌───────────────────────────────────────────┐
             │       Velocity Proxy (Load Balancer)      │
             └──────┬─────────────────────────────┬──────┘
                    │                             │
                    ▼                             ▼
       ┌────────────────────────┐    ┌────────────────────────┐
       │   WyEternol Node A     │    │    WyEternol Node B    │
       │  (RAM: ~250-400 MB)    │    │   (RAM: ~250-400 MB)   │
       │                        │    │                        │
       │ ┌────────────────────┐ │    │ ┌────────────────────┐ │
       │ │  Global Lobby      │ │    │ │  Global Lobby      │ │
       │ └────────────────────┘ │    │ └────────────────────┘ │
       │ ┌────────────────────┐ │    │ ┌────────────────────┐ │
       │ │ Party Instance #1  │ │    │ │ Party Instance #4  │ │
       │ └────────────────────┘ │    │ └────────────────────┘ │
       │ ┌────────────────────┐ │    │ ┌────────────────────┐ │
       │ │ Party Instance #2  │ │    │ │ Party Instance #5  │ │
       │ └────────────────────┘ │    │ └────────────────────┘ │
       └───────────┬────────────┘    └───────────┬────────────┘
                   │                             │
                   └──────────────┬──────────────┘
                                  │
                                  ▼
               ┌─────────────────────────────────────┐
               │ Shared State Layer (Redis / Postgres)│
               │  - Player Inventory & Stats (EXP/LV)│
               │  - Party Matchmaking & Queue        │
               └─────────────────────────────────────┘
```

---

## 3. Core Principles for Maximum Micro-Optimization

### 1. In-Memory Ephemeral Dungeons (Zero Disk I/O)
* **Never write dungeon blocks to disk.**
* Dungeons are generated in RAM using mathematical generators (`WyWorldGenerator`) in **under 5 milliseconds**.
* When all players leave or the dungeon boss dies:
  ```kotlin
  // 1. Teleport remaining players back to Lobby
  players.forEach { it.teleport(lobbySpawn) }
  
  // 2. Unregister instance from InstanceManager immediately
  instanceManager.unregisterInstance(dungeonInstance)
  // Memory is immediately freed for Garbage Collection!
  ```

### 2. Stateless Game Node Design
* Individual server nodes hold **no permanent state** on disk.
* Player inventory, skill points, cooldowns, and quest progress are synced to a central Redis / SQL cache.
* If a WyEternol node crashes or restarts, players are instantly routed to another node without losing data.

### 3. Ticking on Demand (Zero-Cost Idle Instances)
* In Minestom, an instance with **0 players** does not need to execute tick loops.
* Auto-freeze chunks that do not contain players.
* Custom packet-based entities (Display Entities, Text Displays) consume **0 pathfinding CPU cycles**.

---

## 4. Implementation Guidelines in WyEternol Clean Architecture

### In `wyeternol-core` (Domain Layer):
* Define `WyInstance` and `WyInstanceManager` contracts.
* Define `WyDungeonSession` holding party IDs, timer, and completion status.
* Zero Minestom imports!

### In `wyeternol-world` (Generation Layer):
* Write deterministic procedural room builders (e.g. `WyDungeonRoomGenerator`).
* Use pure block math (Stone, Quartz, Obsidian, Boss Dais).

### In `wyeternol-game` (Mechanics Layer):
* Matchmaker service:
  ```kotlin
  fun startDungeon(party: WyParty, dungeonType: DungeonType) {
      val instance = worldManager.createDungeon(dungeonType)
      party.members.forEach { it.switchWorld(instance) }
  }
  ```

### In `wyeternol-server` (Adapter Layer):
* Maps `WyInstance` to Minestom's `InstanceContainer`.
* Configures `LightingChunk` or fast flat lighting per dungeon requirement.
* Handles fast disposal on `unregisterInstance`.

---

## 5. Scaling Metrics & Targets

* **Idle Base Server**: < 100 MB RAM
* **Cost Per Active Dungeon (4 Players)**: ~2 - 5 MB RAM
* **Capacity of a 4 GB VPS**: **~500 - 800 Concurrent Players across 150+ instanced dungeons** (Compared to Paper which maxes out at ~50-80 players on the same hardware).
