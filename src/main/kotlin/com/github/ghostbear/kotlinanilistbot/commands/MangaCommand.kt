package com.github.ghostbear.kotlinanilistbot.commands

import com.github.ghostbear.kotlinanilistbot.commands.base.MediaCommand
import com.github.ghostbear.kotlinanilistbot.model.MediaType

class MangaCommand : MediaCommand() {
    override val mediaType: MediaType = MediaType.MANGA

    override val pattern: Regex = Regex("^<.*>")
}