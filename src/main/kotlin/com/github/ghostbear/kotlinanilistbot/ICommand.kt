package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.entities.Message

interface ICommand {
    val pattern: Regex
    fun matches(message: Message): Boolean = message.contentRaw.matches(pattern)
    fun execute(message: Message)
}
