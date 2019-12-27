package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonAlias

data class Page<T>(
        @JsonAlias("media", "characters", "staff", "studios")
        var list : List<T>
)