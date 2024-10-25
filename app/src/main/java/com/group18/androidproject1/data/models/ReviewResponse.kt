package com.group18.androidproject1.data.models

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("data") val data: List<Review>,
    @SerializedName("pagination") val pagination: Pagination
)

data class Review(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("url") val url: String,
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("review") val review: String,
    @SerializedName("score") val score: Int,
    @SerializedName("is_spoiler") val isSpoiler: Boolean,
    @SerializedName("is_preliminary") val isPreliminary: Boolean,
    @SerializedName("episodes_watched") val episodesWatched: Int?,
    @SerializedName("entry") val reviewAnime: ReviewAnime?,
    @SerializedName("user") val user: User
)

data class ReviewAnime(
    @SerializedName("mal_id") val malId: Int,
    @SerializedName("url") val url: String,
    @SerializedName("title") val title: String,
    @SerializedName("images") val images: Images
)

data class User(
    @SerializedName("username") val username: String,
    @SerializedName("url") val url: String,
    @SerializedName("images") val images: Images
)
