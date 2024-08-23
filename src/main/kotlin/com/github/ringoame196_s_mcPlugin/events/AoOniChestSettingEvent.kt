package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.managers.YmlManager
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.plugin.Plugin
import java.io.File

class AoOniChestSettingEvent(plugin: Plugin) : Listener {
    private val config = plugin.config
    private val ymlMaterial = YmlManager()
    private val aoOniWorld = config.getString("aoOni_world")
    private val gameData = File(plugin.dataFolder, "game.yml")
    private val key = "aoOniChests"

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val block = e.block
        val world = player.world.name

        if (isExecutionAllowed(player, block)) {
            // 設定されたワールドでOPがチェストを置いた場合のみ動かす
            val message = "${ChatColor.GOLD}チェストを登録しました"
            val chestLocation = block.location
            additionChest(chestLocation)
            player.sendMessage(message)
        }
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val player = e.player
        val block = e.block
        if (isExecutionAllowed(player, block)) {
            // 設定されたワールドでOPがチェストを破壊した場合のみ動かす
            val message = "${ChatColor.RED}チェストを削除しました"
            val chestLocation = block.location
            deletionChest(chestLocation)
            player.sendMessage(message)
        }
    }

    private fun isExecutionAllowed(player: Player, block: Block): Boolean {
        val world = player.world.name
        val type = block.type
        return world == aoOniWorld && type == Material.CHEST && player.isOp
    }

    private fun additionChest(location: Location) {
        val chestList = ymlMaterial.acquisitionListValue(gameData, key)
        val chestLocation = "x:${location.x}y:${location.y}z:${location.z}"
        chestList?.add(chestLocation)
        ymlMaterial.setValue(gameData, key, chestList)
    }

    private fun deletionChest(location: Location) {
        val chestList = ymlMaterial.acquisitionListValue(gameData, key)
        val chestLocation = "x:${location.x}y:${location.y}z:${location.z}"
        chestList?.remove(chestLocation)
        ymlMaterial.setValue(gameData, key, chestList)
    }
}
