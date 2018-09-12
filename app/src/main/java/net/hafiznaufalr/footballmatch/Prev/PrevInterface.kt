package net.hafiznaufalr.footballmatch.Prev

import net.hafiznaufalr.footballmatch.Model.Event
import net.hafiznaufalr.footballmatch.Model.Team

interface PrevInterface {
    fun showLoading()
    fun hideLoading()
    fun setEvent(event: List<Event>)
    fun setHomeBadges(teams: List<Team>)
    fun setAwayBadges(teams: List<Team>)
}