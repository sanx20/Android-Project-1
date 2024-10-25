package com.group18.androidproject1.data.repository.review

import com.group18.androidproject1.data.models.ReviewResponse
import com.group18.androidproject1.domain.review.ReviewApiService
import retrofit2.HttpException
import java.io.IOException

class ReviewRepository(private val api: ReviewApiService) {
    suspend fun getTopReviews(page: Int): Result<ReviewResponse> {
        return try {
            val response = api.getTopReviews(page)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to load data"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error"))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error"))
        }
    }
}
