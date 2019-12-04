package com.github.ghostbear.kotlinanilistbot

import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message

abstract class MediaCommand: ICommand, GraphRequest() {

    abstract val mediaType: MediaType

    private val parameters: HashMap<String, Any> = HashMap()

    override fun execute(message: Message) {
        var context = message.contentRaw


        context = context.substring(1, context.length - 1)

        parameters.put("search", context)
        parameters.put("type", mediaType)

        postRequest<Response<Page<Media>>> { _, _, result ->
            val media = result.get().data.page.list.first()
            var url = media.siteUrl
            val reply = "Here is what I found $url"
            message.channel.sendMessage(reply).queue {
                println("Message successfully sent ($url)")
            }
        }
    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 5)) {
                    fieldObject("media", parameters) {
                        field("siteUrl")
                    }
                }
            }
        }
    }
}

class AnimeCommand: MediaCommand() {
    override val mediaType: MediaType = MediaType.ANIME

    override val pattern: Regex = Regex("^\\{.*}$")
}

class MangaCommand: MediaCommand() {
    override val mediaType: MediaType = MediaType.MANGA

    override val pattern: Regex = Regex("^<.*>")
}