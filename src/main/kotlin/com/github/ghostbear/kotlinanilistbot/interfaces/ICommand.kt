package com.github.ghostbear.kotlinanilistbot.interfaces

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed

interface ICommand {
    val pattern: Regex
    fun matches(message: Message): Boolean = message.contentRaw.matches(pattern)
    fun execute(message: Message)
    fun sendMessage(channel: MessageChannel, message: MessageEmbed, string: String) = channel.sendMessage(message).queue() { println(string) }
}
