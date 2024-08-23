package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.consts.AoOniConst
import com.github.ringoame196_s_mcPlugin.consts.CommandConst
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabCompleter : TabCompleter {
    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf(CommandConst.START, CommandConst.STOP, CommandConst.SET)
            2 -> when (args[0]) {
                CommandConst.SET -> settingTabCompleter() // setのときのタブ
                else -> mutableListOf()
            }
            else -> mutableListOf()
        }
    }
    private fun settingTabCompleter(): MutableList<String> {
        return mutableListOf(AoOniConst.SETTING_AO_ONI_START_LOCATION, AoOniConst.SETTING_HIROSHI_START_LOCATION)
    }
}
