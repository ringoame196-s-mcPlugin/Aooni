package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.consts.AoOniConst
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.Plugin

class LocationManager(plugin: Plugin) {
    private val config = plugin.config
    private val aoOniWorldName = config.getString(AoOniConst.AO_ONI_WORLD_KEY)
    fun conversionString(location: Location): String {
        return "x:${location.x.toInt() + 0.5}y:${location.y}z:${location.z.toInt() + 0.5}"
    }

    fun resurrectionCoordinate(locationText: String):Location? {
        val world = Bukkit.getWorld(aoOniWorldName ?:return null)
        val regex = Regex("""x:(\d+)y:(\d+)z:(\d+)""")
        val matchResult = regex.find(locationText)

        matchResult?.let {
            val (x, y, z) = it.destructured
            return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
        }
        return null
    }
}
