package com.group18.androidproject1.ui.screens.reviews

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.group18.androidproject1.R
import com.group18.androidproject1.databinding.ActivityReviewsBinding
import com.group18.androidproject1.data.repository.review.ReviewRepository
import com.group18.androidproject1.domain.retrofit.RetrofitInstance
import com.group18.androidproject1.ui.screens.reviews.components.ReviewsAdapter
import com.group18.androidproject1.ui.viewmodels.review.ReviewViewModel
import com.group18.androidproject1.ui.viewmodels.review.ReviewViewModelFactory

class ReviewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewsBinding

    private val reviewViewModel: ReviewViewModel by viewModels {
        ReviewViewModelFactory(ReviewRepository(RetrofitInstance.reviewApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            this.title = getString(R.string.reviews)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val adapter = ReviewsAdapter()
        // Use GridLayoutManager for 2-column grid
        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewReviews.adapter = adapter

        reviewViewModel.reviewList.observe(this, { reviews ->
            Log.d("ReviewsActivity", "Data received: ${reviews.size} reviews")
            adapter.submitList(reviews)
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewReviews.visibility = View.VISIBLE
            binding.errorText.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
        })

        reviewViewModel.errorMessage.observe(this, { error ->
            error?.let {
                Log.e("ReviewsActivity", "Error: $it")
                binding.errorText.text = it
                binding.errorText.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewReviews.visibility = View.GONE
            }
        })

        reviewViewModel.isLoading.observe(this, { isLoading ->
            Log.d("ReviewsActivity", "Loading: $isLoading")
            if (isLoading) {
                if (reviewViewModel.reviewList.value.isNullOrEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    adapter.showLoading()
                }
            } else {
                binding.progressBar.visibility = View.GONE
                adapter.hideLoading()
            }
        })

        binding.retryButton.setOnClickListener {
            Log.d("ReviewsActivity", "Retry button clicked")
            reviewViewModel.fetchReviews()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }
    }
}
