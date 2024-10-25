package com.group18.androidproject1.data.repository.manga

import com.group18.androidproject1.data.models.MangaResponse
import com.group18.androidproject1.domain.manga.MangaApiService
import retrofit2.HttpException
import java.io.IOException

class MangaRepository(private val api: MangaApiService) {
    suspend fun getTopManga(page: Int): Result<MangaResponse> {
        return try {
            val response = api.getTopManga(page)
            Result.success(response)
        } catch (e: IOException) {
            Result.failure(Exception("Network error"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error"))
        }
    }
}
