package com.github.ringoame196_s_mcPlugin.managers

import com.github.ringoame196_s_mcPlugin.AoOniConst
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team

class AoOniTeamManager {
    private val scoreboard = Bukkit.getScoreboardManager()?.mainScoreboard

    fun join(player: Player, teamName: String) {
        val team = scoreboard?.getTeam(teamName) ?: return
        team.addEntry(player.name)
        player.scoreboard = scoreboard
    }

    fun isAoOni(player: Player): Boolean {
        val team = player.scoreboard.getEntryTeam(player.name) // team取得
        return team?.name == AoOniConst.AO_ONI_TEAM_NAME
    }

    fun isHiroshi(player: Player): Boolean {
        val team = player.scoreboard.getEntryTeam(player.name) // team取得
        return team?.name == AoOniConst.HIROSHI_TEAM_NAME
    }

    fun makeTeam(id: String, name: String, color: ChatColor) {
        if (!isExistsTeam(id)) {
            scoreboard?.registerNewTeam(id)?.apply {
                prefix = "[$name]" // prefixの登録
                setColor(color) // カラーの設定
                setAllowFriendlyFire(false) // フレンドリファイヤーoff
                setCanSeeFriendlyInvisibles(true) // 透明化でも見えるように
                setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM) // ネームタグを他のチームから見えないように設定
            }
        }
    }

    fun resetAllPlayer(id: String) {
        if (isExistsTeam(id)) { // チームが存在するか
            val team = scoreboard?.getTeam(id) // チーム取得
            for (entity in team?.entries ?: return) { // チームに入っているentityを全削除
                team.removeEntry(entity)
            }
        }
    }

    private fun isExistsTeam(name: String): Boolean {
        return scoreboard?.getTeam(name) != null
    }
}
