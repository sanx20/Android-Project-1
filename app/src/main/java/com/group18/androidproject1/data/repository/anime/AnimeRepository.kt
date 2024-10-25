package com.group18.androidproject1.data.repository.anime

import com.group18.androidproject1.domain.anime.AnimeApiService
import com.group18.androidproject1.data.models.AnimeResponse
import retrofit2.HttpException
import java.io.IOException

class AnimeRepository(private val api: AnimeApiService) {
    suspend fun getTopAnimes(page: Int): Result<AnimeResponse> {
        return try {
            val response = api.getTopAnimes(page)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(Exception("Network error"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error"))
        }
    }
}
