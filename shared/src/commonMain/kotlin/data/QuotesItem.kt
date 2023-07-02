package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesItem(
    @SerialName("anime")
    val anime: String? = null,
    @SerialName("character")
    val character: String? = null,
    @SerialName("quote")
    val quote: String? = null,
)