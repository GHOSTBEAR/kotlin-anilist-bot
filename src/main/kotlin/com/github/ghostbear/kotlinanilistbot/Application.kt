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
import kotlin.properties.ObservableProperty

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

	var listener: ResponseListener<Media>? = null

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

			if (listener == null) {
				listener = object : ResponseListener<Media> {
					override fun onResponse(response: Media) {
						message.channel.sendMessage("Here is what I found https://anilist.co/${response.type.toString().toLowerCase()}/${response.id}").queue()
					}
				}
			}

			searchMedia(text, type)
		}
	}

	fun searchMedia(text: String, type: MediaType) {
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
					listener?.onResponse(result.get().data.page.list.first())
				}
	}
}

interface ResponseListener<T> {
	fun onResponse(response: T)
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
		@JsonProperty("id") var id: Int = -1,
		@JsonProperty("type") var type: MediaType = MediaType.ANIME
)
