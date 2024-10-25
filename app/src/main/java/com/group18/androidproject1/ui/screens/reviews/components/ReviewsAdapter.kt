package com.group18.androidproject1.ui.screens.reviews.components

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.R
import com.group18.androidproject1.data.models.Review
import com.squareup.picasso.Picasso

class ReviewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val reviewsList = mutableListOf<Review?>()
    private var isLoading = false

    companion object {
        private const val ITEM_TYPE_REVIEW = 1
        private const val ITEM_TYPE_LOADING = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_REVIEW) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
            ReviewsViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReviewsViewHolder) {
            val review = reviewsList[position]
            if (review != null) {
                Log.d("ReviewsAdapter", "Binding review: ${review.user.username}, Anime: ${review.reviewAnime?.title}")
                holder.bind(review)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (reviewsList[position] == null) ITEM_TYPE_LOADING else ITEM_TYPE_REVIEW
    }

    override fun getItemCount(): Int = reviewsList.size

    fun submitList(list: List<Review>) {
        reviewsList.clear()
        reviewsList.addAll(list)
        Log.d("ReviewsAdapter", "submitList called with ${list.size} items")
        notifyDataSetChanged()
    }

    fun showLoading() {
        if (!isLoading) {
            isLoading = true
            reviewsList.add(null)  // Add null item to show loading
            notifyItemInserted(reviewsList.size - 1)
        }
    }

    fun hideLoading() {
        if (isLoading) {
            isLoading = false
            val position = reviewsList.size - 1
            reviewsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.reviewUserName)
        private val reviewTextView: TextView = itemView.findViewById(R.id.reviewText)
        private val scoreTextView: TextView = itemView.findViewById(R.id.reviewScore)
        private val userImageView: ImageView = itemView.findViewById(R.id.reviewUserImage)
        private val animeTitleTextView: TextView = itemView.findViewById(R.id.animeTitle)

        fun bind(review: Review) {
            userNameTextView.text = review.user.username
            reviewTextView.text = review.review
            scoreTextView.text = "Score: ${review.score}"

            val reviewAnime = review.reviewAnime
            animeTitleTextView.text = reviewAnime?.title ?: "Unknown Anime"

            Picasso.get()
                .load(review.user.images.jpg.imageUrl)
                .into(userImageView)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
