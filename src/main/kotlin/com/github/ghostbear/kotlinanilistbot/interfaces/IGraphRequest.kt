package com.github.ghostbear.kotlinanilistbot.interfaces

import com.taskworld.kraph.Kraph

interface IGraphRequest {
    val url: String
    val header: HashMap<String, Any>
    fun query(): Kraph
}