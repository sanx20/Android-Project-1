package com.group18.androidproject1.ui.screens.animes.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.R
import com.group18.androidproject1.data.models.Anime
import com.squareup.picasso.Picasso

class AnimeAdapter(private val onItemClicked: (Anime) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val animeList = mutableListOf<Anime?>()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == animeList.size && isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anime, parent, false)
            AnimeViewHolder(view, onItemClicked)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AnimeViewHolder) {
            val anime = animeList[position]
            anime?.let {
                holder.bind(it)
            }
        }
    }

    override fun getItemCount(): Int = animeList.size + if (isLoading) 1 else 0

    fun submitList(list: List<Anime>) {
        animeList.clear()
        animeList.addAll(list)
        notifyDataSetChanged()
    }

    fun showLoading() {
        isLoading = true
        notifyItemInserted(animeList.size)
    }

    fun hideLoading() {
        isLoading = false
        notifyItemRemoved(animeList.size)
    }

    class AnimeViewHolder(itemView: View, private val onItemClicked: (Anime) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.animeTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.animeImage)

        fun bind(anime: Anime) {
            titleTextView.text = anime.title
            Picasso.get().load(anime.images.jpg.imageUrl).into(imageView)

            itemView.setOnClickListener {
                onItemClicked(anime)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
