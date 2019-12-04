package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.Page
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.Studio
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message

class StudioCommand : ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^>.*<$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Page<Studio>>> { _, _, result ->
            val studio = result.get().data.page.list.first()
            val name = studio.name
            var url = studio.siteUrl
            val embed = EmbedBuilder().setAuthor(name, url)
                    .build()
            message.channel.sendMessage(embed).queue {
                println("[Studio] Message successfully sent, studio $name ($url)")
            }
        }
    }

    override fun query(): Kraph {
        return pagedQuery {
            fieldObject("studios", parameters) {
                field("name")
                field("siteUrl")
            }
        }
    }
}