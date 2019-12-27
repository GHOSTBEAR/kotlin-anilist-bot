package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.Character
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import org.jsoup.Jsoup

class CharacterCommand : ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^\\(.*\\)$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Character>> { request, response, result ->
            val data = result.get().data

            var name = data.name.first
            if (data.name.last != null) {
                name += " ${data.name.last}"
            }

            val embed = EmbedBuilder().setAuthor(name, data.siteUrl)
                    .setDescription(Jsoup.parse(data.description).text())
                    .setThumbnail(data.image.large)
                    .build()
            sendMessage(message.channel, embed, "[Character] Message successfully sent")
        }

    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Character", parameters) {
                    field("id")
                    field("siteUrl")
                    fieldObject("name") {
                        field("first")
                        field("last")
                    }
                    fieldObject("image") {
                        field("large")
                    }
                    field("description")
                }
            }
        }
    }
}