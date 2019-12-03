package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message

class MediaCommand(chain: CommandChain? = null) : CommandChain(chain) {

    override val patterns: ArrayList<Regex> = arrayListOf(Regex("^\\{.*}$"), Regex("^<.*>"))

    override fun execute(message: Message) {
        var context = message.contentRaw

        var mediaType: MediaType? = null

        when {
            context.matches(patterns[0]) -> mediaType = MediaType.ANIME
            context.matches(patterns[1]) -> mediaType = MediaType.MANGA
        }

        if (mediaType != null) {
            context = context.substring(1, context.length - 1)
            AniList.url.httpPost().header(AniList.headers)
                    .body(query(mediaType, context))
                    .responseObject<Response<Page<Media>>> { _, _, result ->
                        val media = result.get().data.page.list.first()
                        val id = media.id
                        val type = media.type.toString().toLowerCase()
                        val reply = "Here is what I found https://anilist.co/${type}/${id}"
                        message.channel.sendMessage(reply).queue {
                            println("Message successfully sent (id: $id, type: $type)")
                        }
                    }
        } else {
            chain?.execute(message)
        }
    }

    fun query(mediaType: MediaType, context: String): String {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 5)) {
                    fieldObject("media", mapOf("search" to context, "type" to mediaType)) {
                        field("id")
                        field("type")
                    }
                }
            }
        }.toRequestString()
    }

}