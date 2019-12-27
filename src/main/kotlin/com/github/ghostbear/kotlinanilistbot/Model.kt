package com.github.ghostbear.kotlinanilistbot

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("data")
data class Response<T>(
        @JsonAlias("Media", "Character", "Staff", "Studio", "Page")
        var data: T
)

data class Page<T>(
        @JsonAlias("media", "characters", "staff", "studios")
        var list : List<T>
)

data class Nodes<T> (
    @JsonProperty("nodes") var nodes: List<T>
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

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Media(
        @JsonProperty("id") var id: Int? = -1,
        @JsonProperty("siteUrl") var siteUrl: String? = "",
        @JsonProperty("title") var title: Title? = null,
        @JsonProperty("coverImage") var coverImage: CoverImage? = null,
        @JsonProperty("status") var status: String? = "",
        @JsonProperty("description") var description: String? = "",
        @JsonProperty("averageScore") var averageScore: Int? = 0
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Character(
        @JsonProperty("id") var id: Int? = -1,
        @JsonProperty("siteUrl") var siteUrl: String? = "",
        @JsonProperty("name") var name: Name,
        @JsonProperty("image") var image: Image,
        @JsonProperty("description") var description: String? = ""
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Staff(
        @JsonProperty("id") var id: Int? = -1,
        @JsonProperty("siteUrl") var siteUrl: String? = "",
        @JsonProperty("name") var name: Name,
        @JsonProperty("image") var image: Image,
        @JsonProperty("description") var description: String? = ""
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Studio(
        @JsonProperty("id") var id: Int? = -1,
        @JsonProperty("name") var name: String,
        @JsonProperty("siteUrl") var siteUrl: String,
        @JsonProperty("media") var media: Nodes<Media>
)

data class Name(
        @JsonProperty("first") var first: String?,
        @JsonProperty("last") var last: String?
)

data class Image(
        @JsonProperty("large") var large: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CoverImage(
        @JsonProperty("large") var large: String,
        @JsonProperty("color") var color: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Title(
        @JsonProperty("romaji") var romaji: String
)