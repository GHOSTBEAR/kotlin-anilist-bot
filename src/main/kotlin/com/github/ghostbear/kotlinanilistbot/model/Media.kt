package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Media(
        @JsonProperty("id")
        var id: Int? = -1,
        @JsonProperty("siteUrl")
        var siteUrl: String? = "",
        @JsonProperty("title")
        var title: Title? = null,
        @JsonProperty("coverImage")
        var coverImage: CoverImage? = null,
        @JsonProperty("status")
        var status: String? = "",
        @JsonProperty("description")
        var description: String? = "",
        @JsonProperty("averageScore")
        var averageScore: Int? = 0
)