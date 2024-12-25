package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.AoOniChestSettingEvent
import com.github.ringoame196_s_mcPlugin.events.AoOniPlayerEvent
import com.github.ringoame196_s_mcPlugin.events.PlayerInteractEvent
import com.github.ringoame196_s_mcPlugin.managers.AoOniTeamManager
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        val plugin = this
        saveDefaultConfig() // config生成
        saveResource("game.yml", false)
        makeTeam() // チーム作成
        val command = getCommand("aooni")
        command!!.setExecutor(Command())
        command.tabCompleter = TabCompleter()
        server.pluginManager.registerEvents(AoOniChestSettingEvent(plugin), plugin)
        server.pluginManager.registerEvents(PlayerInteractEvent(plugin), plugin)
        server.pluginManager.registerEvents(AoOniPlayerEvent(), plugin)
    }

    private fun makeTeam() {
        val aoOniTeamManager = AoOniTeamManager()
        aoOniTeamManager.makeTeam(AoOniConst.AO_ONI_TEAM_NAME, "青鬼", ChatColor.DARK_BLUE)
        aoOniTeamManager.makeTeam(AoOniConst.HIROSHI_TEAM_NAME, "ひろし", ChatColor.GOLD)
        aoOniTeamManager.makeTeam(AoOniConst.ESCAPE_TEAM_NAME, "観戦", ChatColor.YELLOW)
    }
}
