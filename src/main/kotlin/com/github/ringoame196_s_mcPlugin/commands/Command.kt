package com.github.ringoame196_s_mcPlugin.commands

import com.github.ringoame196_s_mcPlugin.consts.AoOniConst
import com.github.ringoame196_s_mcPlugin.consts.CommandConst
import com.github.ringoame196_s_mcPlugin.managers.GameFileManager
import com.github.ringoame196_s_mcPlugin.managers.LocationManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class Command(plugin: Plugin) : CommandExecutor {
    private val gameFileManager = GameFileManager(plugin)
    private val locationManager = LocationManager()
    private val config = plugin.config // configファイル
    private val aoOniWorld = config.getString(AoOniConst.AO_ONI_WORLD_KEY) // 青鬼ワールド

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) { // サブコマンドがなければ そもそも実行しない
            return false
        }

        val subCommand = args[0] // サブコマンド

        when (subCommand) {
            CommandConst.SET -> setting(args, sender) // 座標設定
            else -> sender.sendMessage("${ChatColor.RED}コマンドが間違っています")
        }
        return true
    }

    private fun setting(args: Array<out String>, sender: CommandSender) {
        // 設定可能な箇所をlistに入れておく
        val registerablePlaces = mutableListOf(AoOniConst.SETTING_AO_ONI_START_LOCATION, AoOniConst.SETTING_HIROSHI_START_LOCATION)
        // 情報が足りない場合は実行しない
        if (args.size < 2) {
            return
        }
        val locationName = args[1] // senderが設定したい箇所

        if (!registerablePlaces.contains(locationName)) { // 登録可能箇所以外を入力時
            val message = "${ChatColor.RED}$locationName は登録できません"
            sender.sendMessage(message)
            return
        }

        if (sender !is Player) { // playerのみ実行させる
            val message = "${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です"
            sender.sendMessage(message)
            return
        }

        if (!sender.isOp) { // opを持っていないと実行しない
            val message = "${ChatColor.RED}権限がありません"
            sender.sendMessage(message)
            return
        }

        val playerLocation = sender.location // playerの座標
        val worldName = playerLocation.world?.name // playerがいるワールド名
        if (worldName != aoOniWorld) { // 青鬼ワールドのみ実行可能にする
            val message = "${ChatColor.RED}このワールドでは使用できません"
            sender.sendMessage(message)
            return
        }
        val setLocation = locationManager.conversionString(playerLocation) // locationをString型に変える
        gameFileManager.save(locationName, setLocation) // gameファイルに保存する
        val message = "${ChatColor.AQUA}設定しました"
        sender.sendMessage(message)
    }
}
