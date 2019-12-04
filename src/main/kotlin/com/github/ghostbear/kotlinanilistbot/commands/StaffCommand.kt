package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.Page
import com.github.ghostbear.kotlinanilistbot.Response
import com.github.ghostbear.kotlinanilistbot.Staff
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import com.github.ghostbear.kotlinanilistbot.interfaces.base.GraphRequest
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import org.jsoup.Jsoup

class StaffCommand : ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^\\[.*]$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Page<Staff>>> { _, _, result ->
            val staff = result.get().data.page.list.first()
            val name = staff.name.full
            val url = staff.siteUrl
            val description = staff.description
            val image = staff.image?.large
            val embed = EmbedBuilder().setAuthor(name, url)
                    .setDescription(if (!description.isNullOrEmpty()) Jsoup.parse(description).text() else "No description")
                    .setThumbnail(image ?: "")
                    .build()
            message.channel.sendMessage(embed).queue {
                println("Message successfully sent ($url)")
            }
        }
    }

    override fun query(): Kraph {
        return pagedQuery {
            fieldObject("staff", parameters) {
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