package com.github.ghostbear.kotlinanilistbot

import com.fasterxml.jackson.annotation.JsonProperty

data class Response<T>(
        @JsonProperty("data") var data: Data<T>
)

data class Data<T>(
        @JsonProperty("Page") var page: T
)

data class Page<T: Any>(
        private @JsonProperty("media") var media: List<T>?,
        private @JsonProperty("characters") var characters: List<T>?,
        private @JsonProperty("staff") var staff: List<T>?,
        private @JsonProperty("studios") var studios: List<T>?
) {
    val list: List<T>
    get() {
        media?.let { return it }
        characters?.let { return it }
        staff?.let { return it }
        studios?.let { return it }
        return emptyList()
    }
}

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
        @JsonProperty("title") var title: Title?,
        @JsonProperty("siteUrl") var siteUrl: String?,
        @JsonProperty("description") var description: String?,
        @JsonProperty("coverImage") var coverImage: CoverImage?
)

data class Character(
        @JsonProperty("name") var name: Name,
        @JsonProperty("siteUrl") var siteUrl: String?,
        @JsonProperty("description") var description: String?,
        @JsonProperty("image") var image: Image?
)

data class Staff(
        @JsonProperty("name") var name: Name,
        @JsonProperty("siteUrl") var siteUrl: String?,
        @JsonProperty("description") var description: String?,
        @JsonProperty("image") var image: Image?
)

data class Studio(
        @JsonProperty("name") var name: String,
        @JsonProperty("siteUrl") var siteUrl: String?
)

data class Name(
        @JsonProperty("full") var full: String
)

data class Image(
        @JsonProperty("large") var large: String?
)

data class CoverImage(
        @JsonProperty("large") var large: String?,
        @JsonProperty("color")var color: String?
)

data class Title(
        @JsonProperty("userPreferred") var userPreferred: String?
)