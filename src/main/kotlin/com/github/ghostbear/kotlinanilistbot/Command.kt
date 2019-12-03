package com.github.ghostbear.kotlinanilistbot

import net.dv8tion.jda.api.entities.Message

interface Command {
    fun execute(message: Message)
}

abstract class CommandChain(
        var chain: CommandChain?
) : Command {

    fun chain(command: CommandChain): CommandChain {
        this.chain = command
        return this
    }

    abstract val patterns: ArrayList<Regex>

}
