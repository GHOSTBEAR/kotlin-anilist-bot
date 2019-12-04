package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.interfaces.ICommand
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SearchListener : ListenerAdapter() {

    private var commands = arrayListOf<ICommand>(MangaCommand(), AnimeCommand(), CharacterCommand(), StaffCommand(), StudioCommand())

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author

        if (author.isBot) {
            return
        }

        val message = event.message

        for (command in commands) {
            if (command.matches(message)) {
                command.execute(message)
                break
            }
        }
    }
}