package com.github.ghostbear.kotlinanilistbot

import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun main() {
	val key = System.getenv("DISCORD_KEY")

	JDABuilder(key)
			.addEventListeners(Application())
			.setActivity(Activity.watching("hentai"))
			.setStatus(OnlineStatus.DO_NOT_DISTURB)
			.build()
}

class Application : ListenerAdapter() {

	val url = "https://graphql.anilist.co/"

	override fun onMessageReceived(event: MessageReceivedEvent) {
		val message = event.message
		val author = event.author
		val context = message.contentRaw

		var text: String?
		var type: MediaType? = null

		if (author.isBot) {
			return
		}

		//language=RegExp
		if (context.matches(Regex("^\\{.*}$"))) {
			type = MediaType.ANIME
		}

		//language=RegExp
		if (context.matches(Regex("^<.*>"))) {
			type = MediaType.MANGA
		}

		if (type != null) {
			text = context.substring(1, context.length - 1)

			searchMedia(text, type) { media ->
				message.channel.sendMessage("Here is what I found https://anilist.co/${media.type.toString().toLowerCase()}/${media.id}").queue()
			}
		}
	}

	fun searchMedia(text: String, type: MediaType, listener: (Media) -> Unit) {
		val query = Kraph {
			query {
				fieldObject("Page", mapOf("perPage" to 5)) {
					fieldObject("media", mapOf("search" to text, "type" to type)) {
						field("id")
						field("type")
					}
				}
			}
		}


		url.httpPost().header("content-type" to "application/json", "Accept" to "application/json")
				.body(query.toRequestString()).responseObject<Response<Data<Page<Media>>>> { request, response, result ->
					listener.invoke(result.get().data.page.list.first())
				}
	}
}
