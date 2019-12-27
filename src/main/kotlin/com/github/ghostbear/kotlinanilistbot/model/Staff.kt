package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Staff(
        @JsonProperty("id")
        var id: Int? = -1,
        @JsonProperty("siteUrl")
        var siteUrl: String? = "",
        @JsonProperty("name")
        var name: Name,
        @JsonProperty("image")
        var image: Image,
        @JsonProperty("description")
        var description: String? = ""
)