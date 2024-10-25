package com.group18.androidproject1.data.models
import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    val data: List<Anime>,
    val pagination: Pagination
)
data class Anime(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    @SerializedName("score") val score: Double?,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("images") val images: Images,
    @SerializedName("synopsis") val synopsis: String?
)