package com.group18.androidproject1.ui.screens.animes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.R
import com.group18.androidproject1.data.repository.anime.AnimeRepository
import com.group18.androidproject1.domain.retrofit.RetrofitInstance
import com.group18.androidproject1.ui.screens.anime_details.AnimeDetailsActivity
import com.group18.androidproject1.ui.screens.animes.components.AnimeAdapter
import com.group18.androidproject1.ui.viewmodels.anime.AnimeViewModel
import com.group18.androidproject1.ui.viewmodels.anime.AnimeViewModelFactory

class AnimesActivity : AppCompatActivity() {

    private val animeViewModel: AnimeViewModel by viewModels {
        AnimeViewModelFactory(AnimeRepository(RetrofitInstance.animeApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animes)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAnimes)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val errorText = findViewById<TextView>(R.id.errorText)
        val retryButton = findViewById<Button>(R.id.retryButton)

        val adapter = AnimeAdapter { anime ->
            val intent = Intent(this, AnimeDetailsActivity::class.java).apply {
                putExtra("ANIME_TITLE", anime.title)
                putExtra("ANIME_SYNOPSIS", anime.synopsis)
                putExtra("ANIME_IMAGE", anime.images.jpg.imageUrl)
                putExtra("ANIME_SCORE", anime.score?.toString() ?: "N/A")
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        animeViewModel.animeList.observe(this) { animes ->
            adapter.submitList(animes)
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            errorText.visibility = View.GONE
            retryButton.visibility = View.GONE
        }

        animeViewModel.errorMessage.observe(this) { error ->
            error?.let {
                errorText.text = it
                errorText.visibility = View.VISIBLE
                retryButton.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.GONE
            }
        }

        animeViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                if (animeViewModel.animeList.value.isNullOrEmpty()) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    adapter.showLoading()
                }
            } else {
                progressBar.visibility = View.GONE
                adapter.hideLoading()
            }
        }

        retryButton.setOnClickListener {
            animeViewModel.fetchAnimes()
        }

        // Pagination handling
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!animeViewModel.isLoading.value!! && animeViewModel.hasNextPage.value == true) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        animeViewModel.loadNextPage()
                    }
                }
            }
        })
    }
}
