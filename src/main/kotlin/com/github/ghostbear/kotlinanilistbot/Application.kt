package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity

class Application

fun main() {
	val key = System.getenv("DISCORD_KEY")

	JDABuilder(key)
			.addEventListeners(SearchListener())
			.setActivity(Activity.watching("hentai"))
			.setStatus(OnlineStatus.DO_NOT_DISTURB)
			.build()
}
