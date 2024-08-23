package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.plugin.Plugin
import java.io.File

class GameFileManager(plugin: Plugin) {
    private val ymlManager = YmlManager()
    private val gameFile = File(plugin.dataFolder, "game.yml")

    fun save(key: String, value: Any?) {
        ymlManager.setValue(gameFile, key, value)
    }

    fun acquisitionList(key: String): MutableList<String>? {
        return ymlManager.acquisitionListValue(gameFile, key)?.toMutableList()
    }

    fun acquisitionString(key: String): String? {
        return ymlManager.acquisitionStringValue(gameFile, key)
    }
}
