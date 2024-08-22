package com.github.ringoame196_s_mcPlugin.managers

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scoreboard.Team

class AoOniTeamManager {
    private val scoreboard = Bukkit.getScoreboardManager()?.mainScoreboard

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
