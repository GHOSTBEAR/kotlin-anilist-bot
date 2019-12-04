package com.github.ghostbear.kotlinanilistbot.commands.base

import com.github.ghostbear.kotlinanilistbot.Media
import com.github.ghostbear.kotlinanilistbot.MediaType
import com.github.ghostbear.kotlinanilistbot.Page
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.github.ghostbear.kotlinanilistbot.interfaces.base.postRequest
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

