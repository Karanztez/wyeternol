package dev.wyeternol.core.config

data class ServerConfig(
    val host: String = "0.0.0.0",
    val port: Int = 25598,
    val onlineMode: Boolean = false,
    val serverName: String = "WyEternol Minestom Server",
    val spawnX: Double = 0.0,
    val spawnY: Double = 42.0,
    val spawnZ: Double = 0.0
)
