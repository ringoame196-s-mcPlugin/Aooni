package com.github.ringoame196_s_mcPlugin

import com.github.ringoame196_s_mcPlugin.events.AoOniPlayerEvent
import com.github.ringoame196_s_mcPlugin.events.HiroshiPlayerEvent
import com.github.ringoame196_s_mcPlugin.events.SettingPlayerEvent
import com.github.ringoame196_s_mcPlugin.managers.AoOniTeamManager
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this

    override fun onEnable() {
        super.onEnable()
        saveDefaultConfig() // config生成
        saveResource("game.yml", false)
        makeTeam() // チーム作成
        val command = getCommand("aooni")
        command!!.setExecutor(Command())
        command.tabCompleter = TabCompleter()
        events()
    }

    private fun events() {
        server.pluginManager.registerEvents(SettingPlayerEvent(plugin), plugin)
        server.pluginManager.registerEvents(AoOniPlayerEvent(), plugin)
        server.pluginManager.registerEvents(HiroshiPlayerEvent(), plugin)
    }

    private fun makeTeam() {
        val aoOniTeamManager = AoOniTeamManager()
        aoOniTeamManager.makeTeam(AoOniConst.AO_ONI_TEAM_NAME, "青鬼", ChatColor.DARK_BLUE)
        aoOniTeamManager.makeTeam(AoOniConst.HIROSHI_TEAM_NAME, "ひろし", ChatColor.GOLD)
        aoOniTeamManager.makeTeam(AoOniConst.ESCAPE_TEAM_NAME, "観戦", ChatColor.YELLOW)
    }
}
