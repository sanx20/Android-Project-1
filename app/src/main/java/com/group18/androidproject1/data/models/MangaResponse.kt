package com.group18.androidproject1.data.models

import com.google.gson.annotations.SerializedName
data class MangaResponse(
    val data: List<Manga>,
    val pagination: Pagination
)

data class Manga(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
    @SerializedName("score") val score: Double?,
    @SerializedName("chapters") val chapters: Int?,
    @SerializedName("volumes") val volumes: Int?,
    @SerializedName("images") val images: Images,
    @SerializedName("synopsis") val synopsis: String?
)
