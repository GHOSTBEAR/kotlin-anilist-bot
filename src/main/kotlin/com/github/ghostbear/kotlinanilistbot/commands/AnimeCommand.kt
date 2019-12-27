package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.commands.base.MediaCommand
import com.github.ghostbear.kotlinanilistbot.model.MediaType

class AnimeCommand : MediaCommand() {
    override val mediaType: MediaType = MediaType.ANIME

    override val pattern: Regex = Regex("^\\{.*}$")
}