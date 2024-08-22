package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.AooniChestSettingEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        server.pluginManager.registerEvents(AooniChestSettingEvent(plugin), plugin)
        saveDefaultConfig() // config生成
        saveResource("game.yml", false)
        val command = getCommand("aooni")
        command!!.setExecutor(Command())
        command.tabCompleter = TabCompleter()
    }
}
