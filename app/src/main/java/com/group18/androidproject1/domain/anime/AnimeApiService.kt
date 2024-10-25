package com.group18.androidproject1.domain.anime

import com.group18.androidproject1.data.models.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {
    @GET("top/anime")
    suspend fun getTopAnimes(
        @Query("page") page: Int
    ): AnimeResponse
}
