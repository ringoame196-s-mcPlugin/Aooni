package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.AoOniConst
import com.github.ringoame196_s_mcPlugin.managers.AoOniTeamManager
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class HiroshiPlayerEvent : Listener {
    private val aoOniTeamManager = AoOniTeamManager()

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val plateType = Material.LIGHT_WEIGHTED_PRESSURE_PLATE

        // そもそも ひろしプレイヤーが金の感圧板に乗っていないと動かないように
        if (!aoOniTeamManager.isHiroshi(player)) return
        if (block.type != plateType) return

        val playerItem = player.inventory.itemInMainHand
        val keyItemType = Material.TRIPWIRE_HOOK

        if (playerItem.type == keyItemType) {
            val message = "${ChatColor.AQUA}[脱出] ${player.name}"
            val sound = Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR
            player.gameMode = GameMode.SPECTATOR
            aoOniTeamManager.join(player, AoOniConst.ESCAPE_TEAM_NAME)
            player.playSound(player, sound, 1f, 1f)
            Bukkit.broadcastMessage(message)
        } else {
            e.isCancelled = true
            val message = "${ChatColor.GOLD}カギが必要です"
            val sound = Sound.BLOCK_CHEST_LOCKED
            val textComponent = TextComponent(message)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent)
            player.playSound(player, sound, 1f, 1f)
        }
    }
}
