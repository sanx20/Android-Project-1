package com.group18.androidproject1.ui.screens.manga.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group18.androidproject1.R
import com.group18.androidproject1.data.models.Manga
import com.squareup.picasso.Picasso

class MangaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mangaList = mutableListOf<Manga>()
    private var isLoading = false

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mangaList.size && isLoading) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_manga, parent, false)
            MangaViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MangaViewHolder) {
            val manga = mangaList[position]
            holder.bind(manga)
        }
    }

    override fun getItemCount(): Int = mangaList.size + if (isLoading) 1 else 0

    fun submitList(list: List<Manga>) {
        mangaList.addAll(list)
        notifyDataSetChanged()
    }

    fun showLoading() {
        isLoading = true
        notifyItemInserted(mangaList.size)
    }

    fun hideLoading() {
        isLoading = false
        notifyItemRemoved(mangaList.size)
    }

    class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.mangaTitle)
        private val mangaImageView: ImageView = itemView.findViewById(R.id.mangaImage)

        fun bind(manga: Manga) {
            titleTextView.text = manga.title
            Picasso.get().load(manga.images.jpg.imageUrl).into(mangaImageView)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}