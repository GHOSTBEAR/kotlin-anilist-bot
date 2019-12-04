package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.JDABuilder

class Application

fun main() {
	val key = System.getenv("DISCORD_KEY")

	val jda = JDABuilder(key)
			.addEventListeners(SearchListener())
			.build()

	ActivityController(jda)
}
