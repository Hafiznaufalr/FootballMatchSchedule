package net.hafiznaufalr.footballmatch

import com.google.gson.Gson
import net.hafiznaufalr.footballmatch.Model.MatchResponse
import net.hafiznaufalr.footballmatch.api.ApiReq
import net.hafiznaufalr.footballmatch.api.TSDBApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter (private val view: MainInterface,
                     private val apiRepo: ApiReq,
                     private val gson: Gson) {
    fun getMatchList(type: String?, match: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepo
                    .doRequest(TSDBApi.getMatchesResponse(type, match)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                println("Response => ${data.events}")
                view.showTeamList(data.events)
            }
        }
    }
}