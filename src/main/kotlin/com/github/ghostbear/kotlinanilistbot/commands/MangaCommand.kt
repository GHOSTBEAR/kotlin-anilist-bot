package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.MediaType
import com.github.ghostbear.kotlinanilistbot.commands.base.MediaCommand

class MangaCommand: MediaCommand() {
    override val mediaType: MediaType = MediaType.MANGA

    override val pattern: Regex = Regex("^<.*>")
}