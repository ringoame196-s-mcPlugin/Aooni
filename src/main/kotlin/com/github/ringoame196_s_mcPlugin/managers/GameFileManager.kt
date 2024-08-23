package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.plugin.Plugin
import java.io.File

class GameFileManager(plugin: Plugin) {
    private val ymlManager = YmlManager()
    private val gameFile = File(plugin.dataFolder, "game.yml")

    fun save(key: String, value: String) {
        ymlManager.setValue(gameFile, key, value)
    }
}
