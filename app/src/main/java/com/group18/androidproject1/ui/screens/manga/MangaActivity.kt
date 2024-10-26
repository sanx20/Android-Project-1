package com.group18.androidproject1.ui.screens.manga

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.R
import com.group18.androidproject1.data.repository.manga.MangaRepository
import com.group18.androidproject1.databinding.ActivityMangaBinding
import com.group18.androidproject1.domain.retrofit.RetrofitInstance
import com.group18.androidproject1.ui.screens.manga.components.MangaAdapter
import com.group18.androidproject1.ui.screens.manga_details.MangaDetailsActivity
import com.group18.androidproject1.ui.viewmodels.manga.MangaViewModel
import com.group18.androidproject1.ui.viewmodels.manga.MangaViewModelFactory

class MangaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMangaBinding

    private val mangaViewModel: MangaViewModel by viewModels {
        MangaViewModelFactory(MangaRepository(RetrofitInstance.mangaApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            this.title = getString(R.string.manga)
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        val adapter = MangaAdapter { manga ->
            val intent = Intent(this, MangaDetailsActivity::class.java).apply {
                putExtra("MANGA_TITLE", manga.title)
                putExtra("MANGA_SYNOPSIS", manga.synopsis)
                putExtra("MANGA_IMAGE", manga.images.jpg.imageUrl)
                putExtra("MANGA_SCORE", manga.score?.toString() ?: "N/A")
            }
            startActivity(intent)
        }

        binding.recyclerViewManga.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewManga.adapter = adapter

        mangaViewModel.mangaList.observe(this) { mangas ->
            adapter.submitList(mangas)
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewManga.visibility = View.VISIBLE
            binding.errorText.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
        }

        mangaViewModel.errorMessage.observe(this) { error ->
            error?.let {
                binding.errorText.text = it
                binding.errorText.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewManga.visibility = View.GONE
            }
        }

        mangaViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                if (mangaViewModel.mangaList.value.isNullOrEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    adapter.showLoading()
                }
            } else {
                binding.progressBar.visibility = View.GONE
                adapter.hideLoading()
            }
        }

        binding.retryButton.setOnClickListener {
            mangaViewModel.fetchManga()
        }

        binding.recyclerViewManga.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!mangaViewModel.isLoading.value!! && mangaViewModel.hasNextPage.value == true) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        mangaViewModel.loadNextPage() // Load more items
                    }
                }
            }
        })
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
