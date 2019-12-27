package com.github.ghostbear.kotlinanilistbot.interfaces.base

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.ghostbear.kotlinanilistbot.AniList
import com.github.ghostbear.kotlinanilistbot.interfaces.IGraphRequest
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.Result
import com.taskworld.kraph.Kraph

abstract class GraphRequest : IGraphRequest {
    override val url: String
        get() = AniList.url

    override val header: HashMap<String, Any>
        get() = AniList.headers

    private val defualt = mapOf("page" to 0, "perPage" to 5)

    fun pagedQuery(parameters: Map<String, Any> = defualt, _object: Kraph.FieldBuilder.() -> Unit): Kraph {
        return Kraph {
            query {
                fieldObject("Page", parameters) {
                    _object.invoke(this)
                }
            }
        }
    }

    inline fun <reified T : Any> postRequest(noinline handler: (Request, Response, Result<T, FuelError>) -> Unit) {
        val mapper = ObjectMapper().registerKotlinModule()
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
        return this.url.httpPost().header(header).body(query().toRequestString()).responseObject<T>(mapper, handler::invoke)
    }
}