package com.github.ghostbear.kotlinanilistbot

import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
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
                    jda.presence.activity = Activity.watching(list.random().title?.userPreferred!!)
                    Thread.sleep(1_200_000)
                }
                Thread.sleep(100)
            }
        }

        postRequest<Response<Page<Media>>> { _, _, result ->
            list = result.get().data.page.list
            thread.start()
        }
    }

    override fun query(): Kraph {
        return pagedQuery {
            fieldObject("media", mapOf("sort" to MediaSort.POPULARITY_DESC, "season" to MediaSeason.FALL, "seasonYear" to 2019)) {
                field("id")
                field("type")
                fieldObject("title") {
                    field("userPreferred")
                }
            }
        }
    }
}