package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.Location

class LocationManager {
    fun conversionString(location: Location): String {
        return "x:${location.x.toInt() + 0.5}y:${location.y}z:${location.z.toInt() + 0.5}"
    }
}
