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

enum class MediaSeason {
    WINTER, SPRING, SUMMER, FALL
}

enum class MediaSort {
    ID, ID_DESC, TITLE_ROMAJI, TITLE_ROMAJI_DESC, TITLE_ENGLISH, TITLE_ENGLISH_DESC, TITLE_NATIVE, TITLE_NATIVE_DESC, TYPE, TYPE_DESC, FORMAT, FORMAT_DESC, START_DATE, START_DATE_DESC, END_DATE, END_DATE_DESC, SCORE, SCORE_DESC, POPULARITY, POPULARITY_DESC, TRENDING, TRENDING_DESC, EPISODES, EPISODES_DESC, DURATION, DURATION_DESC, STATUS, STATUS_DESC, CHAPTERS, CHAPTERS_DESC, VOLUMES, VOLUMES_DESC, UPDATED_AT, UPDATED_AT_DESC, SEARCH_MATCH, FAVOURITES, FAVOURITES_DESC
}

data class Media(
        @JsonProperty("id") var id: Int? = -1,
        @JsonProperty("type") var type: MediaType? = MediaType.ANIME,
        @JsonProperty("title") var title: Title?
)

data class Title(
        @JsonProperty("userPreferred") var userPreferred: String?
)