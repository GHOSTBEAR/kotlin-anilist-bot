package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Name(
        @JsonProperty("first")
        var first: String?,
        @JsonProperty("last")
        var last: String?
)