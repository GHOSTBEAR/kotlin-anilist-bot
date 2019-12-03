package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message
import com.github.ghostbear.kotlinanilistbot.AniList.headers as headers
import com.github.ghostbear.kotlinanilistbot.AniList.url as url

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
            url.httpPost().header(headers)
                    .body(query(mediaType, context))
                    .responseObject<Response<Page<Media>>> { _, _, result ->
                        val media = result.get().data.page.list.first()
                        var url = media.siteUrl
                        val reply = "Here is what I found $url"
                        message.channel.sendMessage(reply).queue {
                            println("Message successfully sent ($url)")
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
                        field("siteUrl")
                    }
                }
            }
        }.toRequestString()
    }

}