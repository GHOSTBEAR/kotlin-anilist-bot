package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message
import com.github.ghostbear.kotlinanilistbot.AniList.headers as headers
import com.github.ghostbear.kotlinanilistbot.AniList.url as url

class CharacterCommand(chain: CommandChain? = null) : CommandChain(chain) {
    override val patterns: ArrayList<Regex> = arrayListOf(Regex("^\\(.*\\)$"))

    override fun execute(message: Message) {
        var context = message.contentRaw

        if (context.matches(patterns[0])) {
            context = context.substring(1, context.length - 1)
            url.httpPost().header(headers)
                    .body(query(context))
                    .responseObject<Response<Page<Character>>> { _, _, result ->
                        val character = result.get().data.page.list.first()
                        val url = character.siteUrl
                        message.channel.sendMessage("Here is what I found $url").queue {
                            println("Message successfully sent ($url)")
                        }
                    }
        } else {
            chain?.let { it.execute(message) }
        }
    }

    fun query(context: String): String {
        return Kraph {
            query {
                fieldObject("Page", mapOf("perPage" to 5)) {
                    fieldObject("characters", mapOf("search" to context)) {
                        field("siteUrl")
                    }
                }
            }
        }.toRequestString()
    }
}