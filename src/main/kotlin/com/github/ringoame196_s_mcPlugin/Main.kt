package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.AooniChestSettingEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEvent
import com.github.ringoame196_s_mcPlugin.managers.AooniTeamManager
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        saveDefaultConfig() // config生成
        saveResource("game.yml", false)
        makeTeam()
        val command = getCommand("aooni")
        command!!.setExecutor(Command())
        command.tabCompleter = TabCompleter()
        server.pluginManager.registerEvents(AooniChestSettingEvent(plugin), plugin)
        server.pluginManager.registerEvents(PlayerInteractEvent(plugin), plugin)
    }

    private fun makeTeam() {
        val aooniTeamManager = AooniTeamManager()
        aooniTeamManager.makeTeam("青鬼", ChatColor.DARK_BLUE)
        aooniTeamManager.makeTeam("ひろし", ChatColor.GOLD)
    }
}
