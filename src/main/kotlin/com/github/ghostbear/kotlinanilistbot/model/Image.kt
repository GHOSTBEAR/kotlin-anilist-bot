package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Image(
        @JsonProperty("large")
        var large: String?
)