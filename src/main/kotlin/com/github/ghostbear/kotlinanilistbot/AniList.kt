package com.github.ghostbear.kotlinanilistbot

object AniList {
    const val url = "https://graphql.anilist.co/"
    val headers = hashMapOf<String, Any>("content-type" to "application/json", "Accept" to "application/json")
}