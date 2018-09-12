package net.hafiznaufalr.footballmatch.Next

import com.google.gson.Gson
import net.hafiznaufalr.footballmatch.Model.EventResponse
import net.hafiznaufalr.footballmatch.Model.TeamResponse
import net.hafiznaufalr.footballmatch.api.ApiReq
import net.hafiznaufalr.footballmatch.api.TSDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextDetailPresenter(private val view: NextInterface,
                          private val apiRepo: ApiReq,
                          private val gson: Gson) {
    fun getEventList(eventId: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepo
                    .doRequest(TSDBApi.getEventsResponse(eventId)),
                    EventResponse::class.java
            )

            uiThread {
                println("Response => ${data.events}")
                view.setEvent(data.events)
                view.hideLoading()
            }
        }
    }

    fun getBadges(type: String, teamName: String?){
        view.showLoading()
        doAsync {
            val badgeUrl = gson.fromJson(apiRepo
                    .doRequest(TSDBApi.getTeamsResponse(teamName)),
                    TeamResponse::class.java
            )

            uiThread {
                println("Response => ${badgeUrl}")
                if (type == "home"){
                    view.setHomeBadges(badgeUrl.teams)
                } else if (type == "away"){
                    view.setAwayBadges(badgeUrl.teams)
                }
                view.hideLoading()
            }
        }
    }
}