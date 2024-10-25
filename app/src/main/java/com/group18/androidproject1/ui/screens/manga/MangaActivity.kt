package com.group18.androidproject1.ui.screens.manga

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.data.repository.manga.MangaRepository
import com.group18.androidproject1.databinding.ActivityMangaBinding
import com.group18.androidproject1.domain.retrofit.RetrofitInstance
import com.group18.androidproject1.ui.screens.manga.components.MangaAdapter
import com.group18.androidproject1.ui.viewmodels.manga.MangaViewModel
import com.group18.androidproject1.ui.viewmodels.manga.MangaViewModelFactory

class MangaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMangaBinding

    private val mangaViewModel: MangaViewModel by viewModels {
        MangaViewModelFactory(MangaRepository(RetrofitInstance.mangaApi))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityMangaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the adapter and RecyclerView with GridLayoutManager
        val adapter = MangaAdapter()
        binding.recyclerViewManga.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerViewManga.adapter = adapter

        // Observe ViewModel for Manga list
        mangaViewModel.mangaList.observe(this, { mangas ->
            adapter.submitList(mangas)
            binding.progressBar.visibility = View.GONE
            binding.recyclerViewManga.visibility = View.VISIBLE
            binding.errorText.visibility = View.GONE
            binding.retryButton.visibility = View.GONE
        })

        // Observe ViewModel for error messages
        mangaViewModel.errorMessage.observe(this, { error ->
            error?.let {
                binding.errorText.text = it
                binding.errorText.visibility = View.VISIBLE
                binding.retryButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.recyclerViewManga.visibility = View.GONE
            }
        })

        // Observe loading state
        mangaViewModel.isLoading.observe(this, { isLoading ->
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
        })

        // Retry button logic
        binding.retryButton.setOnClickListener {
            mangaViewModel.fetchManga()
        }

        // Pagination handling
        binding.recyclerViewManga.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!mangaViewModel.isLoading.value!! && mangaViewModel.hasNextPage.value == true) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        mangaViewModel.loadNextPage()
                    }
                }
            }
        })
    }
}
