package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Studio(
        @JsonProperty("id")
        var id: Int? = -1,
        @JsonProperty("name")
        var name: String,
        @JsonProperty("siteUrl")
        var siteUrl: String,
        @JsonProperty("media")
        var media: Nodes<Media>
)