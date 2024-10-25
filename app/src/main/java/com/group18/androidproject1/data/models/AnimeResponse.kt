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
    @SerializedName("synopsis") val synopsis: String?,
    @SerializedName("images") val images: Images,
    @SerializedName("trailer") val trailer: Trailer?,
    @SerializedName("status") val status: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("genres") val genres: List<Genre>?
)

data class Trailer(
    @SerializedName("youtube_id") val youtubeId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("embed_url") val embedUrl: String?
)

data class Genre(
    @SerializedName("name") val name: String
)
