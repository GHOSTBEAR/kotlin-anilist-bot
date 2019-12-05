package com.github.ghostbear.kotlinanilistbot

object AniList {
    const val url = "https://graphql.anilist.co/"
    val headers = hashMapOf<String, Any>("content-type" to "application/json", "Accept" to "application/json", "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
}