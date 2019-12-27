package com.github.ghostbear.kotlinanilistbot.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Nodes<T> (
    @JsonProperty("nodes")
    var nodes: List<T>
)