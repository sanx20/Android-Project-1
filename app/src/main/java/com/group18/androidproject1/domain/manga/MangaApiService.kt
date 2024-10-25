package com.group18.androidproject1.domain.manga
import com.group18.androidproject1.data.models.AnimeResponse
import com.group18.androidproject1.data.models.MangaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MangaApiService {
    @GET("top/manga")
    suspend fun getTopManga(
        @Query("page") page: Int
    ): MangaResponse
}
