package api

import data.QuotesItem
import getHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiService {

    suspend fun getRandomQuote(): QuotesItem? {
        return try {
            val apiCall = getHttpClient().get("https://animechan.xyz/api/random"){
                contentType(ContentType.Application.Json)
            }
            println("bodyAsText "+apiCall.bodyAsText())
            apiCall.body<QuotesItem>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}