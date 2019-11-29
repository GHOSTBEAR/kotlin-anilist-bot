package com.github.ghostbear.kotlinanilistbot

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
}
