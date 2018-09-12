package net.hafiznaufalr.footballmatch.Model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("strTeamBadge") var strTeamBadge: String = ""
)