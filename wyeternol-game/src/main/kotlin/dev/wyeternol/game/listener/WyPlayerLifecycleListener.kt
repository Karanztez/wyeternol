package dev.wyeternol.game.listener

import dev.wyeternol.core.event.WyEventBus
import dev.wyeternol.core.event.WyPlayerChatEvent
import dev.wyeternol.core.event.WyPlayerJoinEvent
import dev.wyeternol.core.event.WyPlayerQuitEvent
import dev.wyeternol.core.server.WyServer
import dev.wyeternol.core.text.Text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.slf4j.LoggerFactory

class WyPlayerLifecycleListener(
    private val server: WyServer
) {
    private val logger = LoggerFactory.getLogger(WyPlayerLifecycleListener::class.java)

    fun register(eventBus: WyEventBus) {
        // Player Join
        eventBus.subscribe<WyPlayerJoinEvent> { event ->
            val player = event.player
            player.sendMessage(
                Component.text("----------------------------------------\n", NamedTextColor.DARK_GRAY)
                    .append(Text.title("★ ยินดีต้อนรับสู่ ${server.config.serverName} ★\n"))
                    .append(Text.muted("Port เชื่อมต่อ: "))
                    .append(Text.info("${server.config.port}\n"))
                    .append(Text.muted("Game Mode: "))
                    .append(Text.success("${player.gameMode}\n"))
                    .append(Component.text("----------------------------------------", NamedTextColor.DARK_GRAY))
            )
            logger.info("[WyEternol] Player connected: {} ({})", player.username, player.uuid)
        }

        // Player Quit
        eventBus.subscribe<WyPlayerQuitEvent> { event ->
            logger.info("[WyEternol] Player disconnected: {}", event.player.username)
        }

        // Player Chat
        eventBus.subscribe<WyPlayerChatEvent> { event ->
            val formatted = Component.text("[", NamedTextColor.DARK_GRAY)
                .append(Component.text(event.player.username, NamedTextColor.GOLD))
                .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                .append(Component.text(event.rawMessage, NamedTextColor.WHITE))

            event.formattedMessage = formatted
            logger.info("[WyEternol Chat] {}: {}", event.player.username, event.rawMessage)
        }
    }
}
