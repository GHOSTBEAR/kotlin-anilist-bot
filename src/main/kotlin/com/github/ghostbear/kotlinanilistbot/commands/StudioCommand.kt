package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.github.ghostbear.kotlinanilistbot.model.MediaSort
import com.github.ghostbear.kotlinanilistbot.model.Response
import com.github.ghostbear.kotlinanilistbot.model.Studio
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import org.jsoup.Jsoup

class StudioCommand : ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^>.*<$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Studio>> { _, _, result ->
            val data = result.get().data
            var description = ""

            for (anime in data.media.nodes) {
                description += "<a href='${anime.siteUrl}'>${anime.title?.romaji}</a> <br>"
            }


            val embed = EmbedBuilder().setAuthor(data.name, data.siteUrl)
                    .setTitle("Popular Anime")
                    .setDescription(Jsoup.parse(description).text())
                    .build()
            sendMessage(message.channel, embed, "[Studio] Message successfully sent")
        }
    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Studio", parameters) {
                    field("id")
                    field("name")
                    field("siteUrl")
                    fieldObject("media", mapOf("isMain" to true, "sort" to MediaSort.POPULARITY_DESC, "perPage" to 5)) {
                        fieldObject("nodes") {
                            field("siteUrl")
                            fieldObject("title") {
                                field("romaji")
                            }
                        }
                    }
                }
            }
        }
    }
}