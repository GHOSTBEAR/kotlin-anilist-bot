package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import kotlin.concurrent.thread

class ActivityController(jda: JDA) {

    var list: List<Media>? = null

    init {
        "https://graphql.anilist.co/".httpPost().header("content-type" to "application/json", "Accept" to "application/json")
                .body(query())
                .responseObject<Response<Page<Media>>> { _, _, result ->
                    result.get().data.page.list
                }

        thread(start = true) {
            while(true) {
                jda.presence.activity = list?.let {
                    Activity.watching(it.random().title?.userPreferred!!)
                }
                Thread.sleep(1_200_000)
            }
        }
    }

    fun query(): String {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 50)) {
                    fieldObject("media", mapOf("sort" to MediaSort.POPULARITY_DESC, "season" to MediaSeason.FALL, "seasonYear" to 2019)) {
                        field("id")
                        field("type")
                        fieldObject("title") {
                            field("userPreferred")
                        }
                    }
                }
            }
        }.toRequestString()
    }
}