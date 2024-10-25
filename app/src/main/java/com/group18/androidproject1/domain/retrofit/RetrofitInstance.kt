package com.group18.androidproject1.domain.retrofit

import com.group18.androidproject1.domain.anime.AnimeApiService
import com.group18.androidproject1.domain.manga.MangaApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val animeApi: AnimeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApiService::class.java)
    }

    val mangaApi: MangaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MangaApiService::class.java)
    }
}
