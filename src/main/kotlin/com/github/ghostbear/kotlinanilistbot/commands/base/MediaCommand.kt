package com.github.ghostbear.kotlinanilistbot.commands.base

import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.github.ghostbear.kotlinanilistbot.model.Media
import com.github.ghostbear.kotlinanilistbot.model.MediaType
import com.github.ghostbear.kotlinanilistbot.model.Response
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import org.jsoup.Jsoup
import java.awt.Color

abstract class MediaCommand : ICommand, GraphRequest() {

    abstract val mediaType: MediaType

    private val parameters: HashMap<String, Any> = HashMap()

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)
        parameters.put("type", mediaType)

        postRequest<Response<Media>> { _, _, result ->
            println("Got a response!")
            val data = result.get().data
            val embed = EmbedBuilder().setAuthor(data.title?.romaji, data.siteUrl)
                    .setThumbnail(data.coverImage?.large)
                    .setDescription(Jsoup.parse(data.description).text())
                    .setColor(Color.decode(data.coverImage?.color))
                    .build()
            sendMessage(message.channel, embed, "[Media] Message successfully sent")
        }
    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Media", parameters) {
                    field("id")
                    field("siteUrl")
                    fieldObject("title") {
                        field("romaji")
                    }
                    fieldObject("coverImage") {
                        field("large")
                        field("color")
                    }
                    field("status")
                    field("description")
                    field("averageScore")
                }
            }
        }
    }
}

