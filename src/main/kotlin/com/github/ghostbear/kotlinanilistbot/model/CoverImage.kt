package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CoverImage(
        @JsonProperty("large")
        var large: String,
        @JsonProperty("color")
        var color: String
)