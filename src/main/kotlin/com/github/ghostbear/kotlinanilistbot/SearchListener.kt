package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SearchListener : ListenerAdapter() {

    private var commands = arrayListOf<ICommand>(MangaCommand(), AnimeCommand(), CharacterCommand(), StaffCommand())

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author

        if (author.isBot) {
            return
        }

        val message = event.message

        for (command in commands) {
            if (command.matches(message)) {
                command.execute(message)
            }
        }
    }
}