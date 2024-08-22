package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class YmlManager {
    private fun acquisitionYml(file: File): YamlConfiguration {
        return YamlConfiguration.loadConfiguration(file)
    }
    fun acquisitionListValue(file: File, key: String): MutableList<String>? {
        val ymlFile = acquisitionYml(file)
        return ymlFile.getStringList(key)
    }
    fun acquisitionStringValue(file: File, key: String): String? {
        val ymlFile = acquisitionYml(file)
        return ymlFile.getString(key)
    }
    fun setValue(file: File, key: String, value: Any?) {
        val ymlFile = acquisitionYml(file)
        ymlFile.set(key, value)
        ymlFile.save(file)
    }
}
