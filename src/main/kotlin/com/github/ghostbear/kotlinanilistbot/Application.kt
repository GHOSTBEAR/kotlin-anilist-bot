package com.github.ghostbear.kotlinanilistbot

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.taskworld.kraph.Kraph
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

fun main(args: Array<String>) {
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

		if (author.isBot) {
			return
		}

		//language=RegExp
		if (context.matches(Regex("^\\{.*}$"))) {
			TODO("Implement anime search")
		}

		//language=RegExp
		if (context.matches(Regex("^<.*}>"))) {
			TODO("Implement manga search")
		}
	}

	fun searchMedia(text: String, type: MediaType) {
		val query = Kraph {
			query {
				fieldObject("Page", mapOf("perPage" to 5)) {
					fieldObject("media", mapOf("search" to text, "type" to type)) {
						field("id")
					}
				}
			}
		}

		url.httpPost().header("content-type" to "application/json", "Accept" to "application/json")
				.body(query.toRequestString()).responseObject<Response<Data<Page<Media>>>> { request, response, result ->
					println(response)
					println(result)
				}
	}
}

enum class MediaType {
	ANIME, MANGA
}

data class Response<T>(
		@JsonProperty("data") var data: T
)

data class Data<T>(
		@JsonProperty("Page") var page: T
)

data class Page<T>(
		@JsonProperty("media") var list: List<T> = ArrayList()
)

data class Media(
		@JsonProperty("id") var id: Int = -1
)
