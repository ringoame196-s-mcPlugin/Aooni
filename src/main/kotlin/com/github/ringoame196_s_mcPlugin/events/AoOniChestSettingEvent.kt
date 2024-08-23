package com.github.ringoame196_s_mcPlugin.events

import com.github.ringoame196_s_mcPlugin.consts.AoOniConst
import com.github.ringoame196_s_mcPlugin.managers.GameFileManager
import com.github.ringoame196_s_mcPlugin.managers.LocationManager
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

class AoOniChestSettingEvent(plugin: Plugin) : Listener {
    private val config = plugin.config
    private val gameFileManager = GameFileManager(plugin)
    private val locationManager = LocationManager(plugin)
    private val aoOniWorld = config.getString(AoOniConst.AO_ONI_WORLD_KEY)
    private val key = AoOniConst.AO_ONI_CHEST_KEY

    @EventHandler
    fun onBlockPlace(e: BlockPlaceEvent) {
        val player = e.player
        val block = e.block

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
        val chestList = gameFileManager.acquisitionList(key)
        val chestLocation = locationManager.conversionString(location) // チェストの座標
        chestList?.add(chestLocation)
        gameFileManager.save(key, chestList)
    }

    private fun deletionChest(location: Location) {
        val chestList = gameFileManager.acquisitionList(key)
        val chestLocation = locationManager.conversionString(location) // チェストの座標
        chestList?.remove(chestLocation)
        gameFileManager.save(key, chestList)
    }
}
