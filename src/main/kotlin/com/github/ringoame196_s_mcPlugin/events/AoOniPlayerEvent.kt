package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.AoOniConst
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.Plugin

class AoOniPlayerEvent(plugin: Plugin) : Listener {
    @EventHandler
    fun onPlayerJump(e: PlayerMoveEvent) { // ジャンプ
        val player = e.player
        if (isAoOni(player)) { // 青鬼だった場合
            val fromY = e.from.y.toFloat() // 移動前のy
            val y = e.to?.y?.toFloat() ?: return // 現在のy
            val difference = y - fromY // yの差を求める

            if (isJumpInvalid(difference)) { // 半ブロックの高さ(0.5)以外でy座標が変わる場合無効にする(ジャンプ無効)
                e.isCancelled = true
                val message = "${ChatColor.YELLOW}青鬼はジャンプが無効になっています"
                val textComponent = TextComponent(message)
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, textComponent)
            }
        }
    }

    private fun isAoOni(player: Player): Boolean {
        val team = player.scoreboard.getEntryTeam(player.name) // team取得
        return team?.name == AoOniConst.AoOniTeamName
    }

    private fun isJumpInvalid(difference: Float): Boolean { // ジャンプ無効にするか
        return difference > 0.0f && difference != 0.5f
    }

    @EventHandler
    fun onEntityDamage(e: EntityDamageEvent) { // ダメージ
        val player = e.entity as? Player ?: return
        if (isAoOni(player)) { // 青鬼だった場合
            e.isCancelled = true // 無敵
        }
    }
}
