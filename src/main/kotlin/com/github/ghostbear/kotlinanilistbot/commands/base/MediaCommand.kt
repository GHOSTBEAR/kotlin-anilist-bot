package com.github.ghostbear.kotlinanilistbot.commands.base

import com.github.ghostbear.kotlinanilistbot.Media
import com.github.ghostbear.kotlinanilistbot.MediaType
import com.github.ghostbear.kotlinanilistbot.Page
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
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

        postRequest<Response<Page<Media>>> { _, _, result ->
            val media = result.get().data.page.list.first()
            val title = media.title?.userPreferred
            val url = media.siteUrl
            val coverImage = media.coverImage?.large
            val description = media.description
            val color = media.coverImage?.color
            val embed = EmbedBuilder().setAuthor(title, url)
                    .setThumbnail(coverImage)
                    .setDescription(Jsoup.parse(description).text())
                    .setColor(Color.decode(color))
                    .build()
            sendMessage(message.channel, embed, "[Media] Message successfully sent, media $title ($url)")
        }
    }

    override fun query(): Kraph {
        return pagedQuery {
            fieldObject("media", parameters) {
                fieldObject("title") {
                    field("userPreferred")
                }
                field("siteUrl")
                field("description")
                fieldObject("coverImage") {
                    field("large")
                    field("color")
                }

            }
        }
    }
}

