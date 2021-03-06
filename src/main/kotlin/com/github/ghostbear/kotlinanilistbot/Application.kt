package com.github.ghostbear.kotlinanilistbot

import com.github.ghostbear.kotlinanilistbot.commands.*
import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Application : ListenerAdapter() {

    private var commands = arrayListOf<ICommand>(MangaCommand(), AnimeCommand(), CharacterCommand(), StaffCommand(), StudioCommand())

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author

        if (author.isBot) {
            return
        }

        val message = event.message

        println("[Application] onMessageReceived")

        for (command in commands) {
            if (command.matches(message)) {
                println("[Application] Found command, executing...")
                command.execute(message)
                break
            }
        }
    }
}

fun main() {
    val key = System.getenv("DISCORD_KEY")

    val jda = JDABuilder(key)
            .addEventListeners(Application())
            .build()

    ActivityController(jda)
}
