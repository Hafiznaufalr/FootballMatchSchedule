package net.hafiznaufalr.footballmatch.api

import java.net.URL

class ApiReq {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}