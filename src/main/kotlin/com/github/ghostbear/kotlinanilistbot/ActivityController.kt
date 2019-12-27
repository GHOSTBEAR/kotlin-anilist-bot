package com.github.ghostbear.kotlinanilistbot

import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.github.ghostbear.kotlinanilistbot.model.*
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import kotlin.concurrent.thread

class ActivityController(jda: JDA) : GraphRequest() {

    var list: List<Media> = emptyList()

    init {
        val thread = thread {
            while (true) {
                if (list.isNotEmpty()) {
                    jda.presence.activity = Activity.watching(list.random().title?.romaji!!)
                    Thread.sleep(1_200_000)
                }
                Thread.sleep(100)
            }
        }

        postRequest<Response<Page<Media>>> { _, _, result ->
            list = result.get().data.list
            thread.start()
        }
    }

    override fun query(): Kraph {
        return pagedQuery {
            fieldObject("media", mapOf("sort" to MediaSort.POPULARITY_DESC, "season" to MediaSeason.FALL, "seasonYear" to 2019)) {
                fieldObject("title") {
                    field("romaji")
                }
            }
        }
    }
}