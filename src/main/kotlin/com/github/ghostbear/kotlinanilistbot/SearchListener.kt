package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SearchListener : ListenerAdapter() {

    private var commandChain: DiscordCommandChain

    init {
        commandChain = MediaCommand()
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author = event.author

        if (author.isBot) {
            return
        }

        commandChain.execute(event.message)
    }
}