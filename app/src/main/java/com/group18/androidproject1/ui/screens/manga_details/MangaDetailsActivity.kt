package com.group18.androidproject1.ui.screens.manga_details

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.group18.androidproject1.R
import com.squareup.picasso.Picasso

class MangaDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_details)

        val titleTextView: TextView = findViewById(R.id.mangaDetailTitle)
        val synopsisTextView: TextView = findViewById(R.id.mangaDetailSynopsis)
        val scoreTextView: TextView = findViewById(R.id.mangaDetailScore)
        val mangaImageView: ImageView = findViewById(R.id.mangaDetailImage)

        val mangaTitle = intent.getStringExtra("MANGA_TITLE")
        val mangaSynopsis = intent.getStringExtra("MANGA_SYNOPSIS")
        val mangaScore = intent.getStringExtra("MANGA_SCORE")
        val mangaImage = intent.getStringExtra("MANGA_IMAGE")

        titleTextView.text = mangaTitle
        synopsisTextView.text = mangaSynopsis
        scoreTextView.text = "Score: $mangaScore"

        Picasso.get().load(mangaImage).into(mangaImageView)
    }
}
