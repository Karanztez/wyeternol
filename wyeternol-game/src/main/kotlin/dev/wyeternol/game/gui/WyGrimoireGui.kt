package dev.wyeternol.game.gui

import dev.wyeternol.core.gui.WyGui
import dev.wyeternol.core.gui.WyGuiItem
import dev.wyeternol.core.text.Text

class WyGrimoireGui : WyGui {
    override val title: String = "§6§lCelestial Grimoire §8» §fQuest & Arcana"
    override val rows: Int = 6

    override val items: Map<Int, WyGuiItem> = buildMap {
        // --- [LEFT PAGE: Quests & Hero Stats] ---
        put(
            10,
            WyGuiItem(
                materialId = "minecraft:written_book",
                name = "§e§l📜 [ภารกิจหลัก] สำรวจวิหารลอยฟ้า",
                lore = listOf(
                    "§7สำรวจใจกลางแท่นเกิดเกาะลอยฟ้าโบราณ",
                    "§7และค้นพบความลับแห่งพลังดวงดาว",
                    "",
                    "§6รางวัล:",
                    " §f• §a+500 EXP",
                    " §f• §b1x Celestial Core",
                    "",
                    "§e⚡ [คลิกเพื่อส่งภารกิจและรับรางวัล]"
                ),
                glowing = true
            ) { player ->
                player.playSound("minecraft:entity.player.levelup", 1.0f, 1.2f)
                player.sendMessage(Text.success("🎉 [Grimoire] คุณสำเร็จภารกิจ 'สำรวจวิหารลอยฟ้า' ได้รับ +500 EXP และ Celestial Core!"))
            }
        )

        put(
            11,
            WyGuiItem(
                materialId = "minecraft:netherite_sword",
                name = "§c§l⚔️ [ภารกิจล่า] ปราบอสูรหมอกทมิฬ",
                lore = listOf(
                    "§7กำจัดมอนสเตอร์ในดันเจี้ยนเงา",
                    "§7ความคืบหน้า: §e0/5 ตัว",
                    "",
                    "§6รางวัล: §a+250 Gold",
                    "",
                    "§8[คลิกเพื่อดูพิกัดดันเจี้ยน]"
                )
            ) { player ->
                player.playSound("minecraft:item.armor.equip_diamond", 1.0f, 1.0f)
                player.sendMessage(Text.info("🗺️ [Grimoire] พิกัดดันเจี้ยนเงา: (X: 150, Y: 45, Z: -200)"))
            }
        )

        put(
            12,
            WyGuiItem(
                materialId = "minecraft:amethyst_shard",
                name = "§d§l💎 [ภารกิจประจำวัน] นักสะสมศิลาแสงดาว",
                lore = listOf(
                    "§7รวบรวม Amethyst Shard ให้ครบ 10 ชิ้น",
                    "§7ความคืบหน้า: §a10/10 ชิ้น (พร้อมส่ง!)",
                    "",
                    "§6รางวัล: §d+100 Arcana Points",
                    "",
                    "§a⚡ [คลิกเพื่อส่งมอบ]"
                ),
                glowing = true
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.chime", 1.0f, 1.5f)
                player.sendMessage(Text.success("✨ [Grimoire] ส่งมอบศิลาเรียบร้อย! ได้รับ +100 Arcana Points"))
            }
        )

        put(
            28,
            WyGuiItem(
                materialId = "minecraft:enchanted_golden_apple",
                name = "§6§l👑 ระดับพลังเวทย์ (Arcana Mastery)",
                lore = listOf(
                    "§7สถานะตัวละครปัจจุบัน:",
                    " §7• §fฉายา: §bArchmage III",
                    " §7• §fเลเวลพลังเวทย์: §6Lv. 35",
                    " §7• §fพลังเวทย์สูงสุด: §d+300 Arcana"
                ),
                glowing = true
            )
        )

        put(
            29,
            WyGuiItem(
                materialId = "minecraft:glistering_melon_slice",
                name = "§a§l💖 พลังชีวิตสูงสุด (Max HP)",
                lore = listOf(
                    " §7• §fพลังชีวิต: §a10,000 / 10,000 HP",
                    " §7• §fอัตราฟื้นฟู: §a+150 HP/วินาที"
                )
            )
        )

        put(
            30,
            WyGuiItem(
                materialId = "minecraft:experience_bottle",
                name = "§b§l💧 หลอดพลังมานา (Mana Pool)",
                lore = listOf(
                    " §7• §fมานาปัจจุบัน: §b450 / 450 MP",
                    " §7• §fอัตราฟื้นฟู: §b+25 MP/วินาที"
                )
            )
        )

        // --- [RIGHT PAGE: Mystical Gemstone Sockets] ---
        put(
            14,
            WyGuiItem(
                materialId = "minecraft:amethyst_block",
                name = "§d§l🔮 Amethyst Focus",
                lore = listOf("§7สลักลงในคทาเวทย์", "§d+15% Spell Critical Chance")
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.hit", 1.0f, 1.2f)
                player.sendMessage(Text.info("🔮 [Gemstone] เลือกช่อง Amethyst Focus (+15% Spell Crit)"))
            }
        )

        put(
            15,
            WyGuiItem(
                materialId = "minecraft:emerald",
                name = "§a§l💚 Emerald Life Stone",
                lore = listOf("§7สลักลงในเกราะอก", "§a+500 Max HP")
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.hit", 1.0f, 1.2f)
                player.sendMessage(Text.info("💚 [Gemstone] เลือกช่อง Emerald Life Stone (+500 HP)"))
            }
        )

        put(
            16,
            WyGuiItem(
                materialId = "minecraft:diamond",
                name = "§b§l💎 Diamond Aegis",
                lore = listOf("§7สลักลงในโล่", "§b+80 Defense Armor")
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.hit", 1.0f, 1.2f)
                player.sendMessage(Text.info("💎 [Gemstone] เลือกช่อง Diamond Aegis (+80 Defense)"))
            }
        )

        put(
            23,
            WyGuiItem(
                materialId = "minecraft:redstone",
                name = "§c§l🔥 Fire Ruby",
                lore = listOf("§7สลักลงในดาบ", "§c+35 Flame Burn Damage")
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.hit", 1.0f, 1.2f)
                player.sendMessage(Text.info("🔥 [Gemstone] เลือกช่อง Fire Ruby (+35 Flame DMG)"))
            }
        )

        put(
            24,
            WyGuiItem(
                materialId = "minecraft:nether_star",
                name = "§f§l⭐ Nether Star Catalyst",
                lore = listOf("§e[แกนพลังสูงสุดแห่ง WyEternol]", "§fปลดล็อกสกิล Ultimate: Celestial Nova"),
                glowing = true
            ) { player ->
                player.playSound("minecraft:block.beacon.activate", 1.0f, 1.5f)
                player.sendMessage(Text.success("⭐ [Catalyst] แกนพลังดวงดาวพร้อมใช้งาน! สกิล Celestial Nova พร้อมร่าย!"))
            }
        )

        put(
            25,
            WyGuiItem(
                materialId = "minecraft:lapis_lazuli",
                name = "§9§l🌀 Wind Sapphire",
                lore = listOf("§7สลักลงในรองเท้า", "§9+20% Movement Speed")
            ) { player ->
                player.playSound("minecraft:block.amethyst_block.hit", 1.0f, 1.2f)
                player.sendMessage(Text.info("🌀 [Gemstone] เลือกช่อง Wind Sapphire (+20% Speed)"))
            }
        )

        // --- [ACTION & CONTROL BUTTONS] ---
        put(
            40,
            WyGuiItem(
                materialId = "minecraft:beacon",
                name = "§6§l✨ [ร่ายเวทมนตร์อัปเกรด / Infuse Arcana]",
                lore = listOf(
                    "§7ผสานพลังศิลาเวทมนตร์ทั้งหมด",
                    "§7เข้าสู่ชุดเกราะและอาวุธของผู้เล่น",
                    "",
                    "§e⚡ [คลิกเพื่อเริ่มกระบวนการตีบวกเวทมนตร์]"
                ),
                glowing = true
            ) { player ->
                player.playSound("minecraft:block.enchantment_table.use", 1.0f, 1.0f)
                player.sendMessage(Text.success("✨ [Grimoire] พลังเวทมนตร์แห่งศิลาถูกผสานเข้ากับตัวละครเรียบร้อยแล้ว! (All Stats Boosted)"))
            }
        )

        put(
            49,
            WyGuiItem(
                materialId = "minecraft:barrier",
                name = "§c§l❌ [ปิดสมุดบันทึก / Close Grimoire]",
                lore = listOf("§7คลิกเพื่อปิดหน้าต่างนี้")
            ) { player ->
                player.playSound("minecraft:ui.button.click", 1.0f, 1.0f)
                player.closeGui()
            }
        )
    }
}
