package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("data")
data class Response<T>(
        @JsonAlias("Media", "Character", "Staff", "Studio", "Page")
        var data: T
)
