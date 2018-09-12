package net.hafiznaufalr.footballmatch

import net.hafiznaufalr.footballmatch.Model.Match

interface MainInterface {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Match>)
}