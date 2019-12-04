package com.github.ghostbear.kotlinanilistbot

import com.github.ghostbear.kotlinanilistbot.commands.SearchListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity

class Application

fun main() {
	val key = System.getenv("DISCORD_KEY")

	val jda = JDABuilder(key)
			.addEventListeners(SearchListener())
			.setActivity(Activity.playing("Cookie Clicker"))
			.build()

	ActivityController(jda)
}
