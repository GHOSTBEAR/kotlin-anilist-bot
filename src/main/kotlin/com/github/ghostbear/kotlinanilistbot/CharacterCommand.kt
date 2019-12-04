package com.github.ghostbear.kotlinanilistbot

import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message

class CharacterCommand: ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^\\(.*\\)$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Page<Character>>> { request, response, result ->
            val character = result.get().data.page.list.first()
            val url = character.siteUrl
            message.channel.sendMessage("Here is what I found $url").queue {
                println("Message successfully sent ($url)")
            }
        }

    }

    override fun query(): Kraph {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 5)) {
                    fieldObject("characters", parameters) {
                        field("siteUrl")
                    }
                }
            }
        }
    }
}