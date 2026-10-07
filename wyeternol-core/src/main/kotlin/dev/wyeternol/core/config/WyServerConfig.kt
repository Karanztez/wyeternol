package dev.wyeternol.core.config

import dev.wyeternol.core.model.WyPosition

data class WyServerConfig(
    val host: String = "0.0.0.0",
    val port: Int = 25598,
    val onlineMode: Boolean = false,
    val serverName: String = "WyEternol",
    val spawnPosition: WyPosition = WyPosition(0.0, 42.0, 0.0)
)
