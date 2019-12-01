package com.github.ghostbear.kotlinanilistbot

import com.fasterxml.jackson.annotation.JsonProperty

data class Response<T>(
        @JsonProperty("data") var data: Data<T>
)

data class Data<T>(
        @JsonProperty("Page") var page: T
)

data class Page<T>(
        @JsonProperty("media") var list: List<T> = ArrayList()
)

enum class MediaType {
    ANIME, MANGA
}

data class Media(
        @JsonProperty("id") var id: Int = -1,
        @JsonProperty("type") var type: MediaType = MediaType.ANIME
)