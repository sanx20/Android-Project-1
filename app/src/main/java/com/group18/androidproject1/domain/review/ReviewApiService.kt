package com.group18.androidproject1.domain.review

import com.group18.androidproject1.data.models.ReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewApiService {
    @GET("top/reviews")
    suspend fun getTopReviews(
        @Query("page") page: Int
    ): Response<ReviewResponse>
}
