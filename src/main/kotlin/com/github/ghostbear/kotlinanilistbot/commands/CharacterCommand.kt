package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.Character
import com.github.ghostbear.kotlinanilistbot.Page
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import org.jsoup.Jsoup

class CharacterCommand: ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^\\(.*\\)$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Page<Character>>> { request, response, result ->
            val character = result.get().data.page.list.first()
            val name = character.name.full
            val url = character.siteUrl
            val description = character.description
            val image = character.image?.large
            val embed = EmbedBuilder().setAuthor(name, url)
                    .setDescription(Jsoup.parse(description).text())
                    .setThumbnail(image)
                    .build()
            message.channel.sendMessage(embed).queue {
                println("Message successfully sent ($url)")
            }
        }

    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 5)) {
                    fieldObject("characters", parameters) {
                        fieldObject("name") {
                            field("full")
                        }
                        field("siteUrl")
                        field("description")
                        fieldObject("image") {
                            field("large")
                        }
                    }
                }
            }
        }
    }
}