package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.MediaType
import com.github.ghostbear.kotlinanilistbot.commands.base.MediaCommand

class AnimeCommand: MediaCommand() {
    override val mediaType: MediaType = MediaType.ANIME

    override val pattern: Regex = Regex("^\\{.*}$")
}