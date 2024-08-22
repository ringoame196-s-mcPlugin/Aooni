package com.github.ringoame196_s_mcPlugin.events

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin

class PlayerInteractEvent(plugin: Plugin) : Listener {
    private val config = plugin.config
    private val aooniWorld = config.getString("aooni_world")
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return

        if (isExecutionAllowed(player, block)) {
            // ドアを開けないようにキャンセルする
            e.isCancelled = true
            val message = "${ChatColor.GOLD}カギが必要です"
            val sound = Sound.BLOCK_CHEST_LOCKED
            val textComponent = TextComponent(message)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent)
            player.playSound(player, sound, 1f, 1f)
        }
    }

    private fun isExecutionAllowed(player: Player, block: Block): Boolean {
        // ドアをロックする条件に合っているか返す
        val world = player.world.name
        val plateType = Material.LIGHT_WEIGHTED_PRESSURE_PLATE
        val keyItemType = Material.TRIPWIRE_HOOK
        val blockType = block.type
        val item = player.inventory.itemInMainHand
        val itemType = item.type
        val gamemode = player.gameMode
        return world == aooniWorld && blockType == plateType && gamemode != GameMode.CREATIVE && itemType != keyItemType
    }
}
