package com.github.ghostbear.kotlinanilistbot

import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.entities.Message

class StaffCommand : ICommand, GraphRequest() {
    private val parameters: HashMap<String, Any> = HashMap()

    override val pattern: Regex = Regex("^\\[.*]$")

    override fun execute(message: Message) {
        var context = message.contentRaw

        context = context.substring(1, context.length - 1)

        parameters.put("search", context)

        postRequest<Response<Page<Staff>>> { _, _, result ->
            val staff = result.get().data.page.list.first()
            var url = staff.siteUrl
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
                    fieldObject("staff", parameters) {
                        field("siteUrl")
                    }
                }
            }
        }
    }
}