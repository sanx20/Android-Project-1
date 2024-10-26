package com.group18.androidproject1.ui.screens.anime_details

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.group18.androidproject1.R
import com.squareup.picasso.Picasso

class AnimeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_details)

        val titleTextView = findViewById<TextView>(R.id.animeDetailTitle)
        val synopsisTextView = findViewById<TextView>(R.id.animeDetailSynopsis)
        val imageView = findViewById<ImageView>(R.id.animeDetailImage)
        val scoreTextView = findViewById<TextView>(R.id.animeDetailScore)

        // Get data from intent
        val title = intent.getStringExtra("ANIME_TITLE")
        val synopsis = intent.getStringExtra("ANIME_SYNOPSIS")
        val imageUrl = intent.getStringExtra("ANIME_IMAGE")
        val score = intent.getStringExtra("ANIME_SCORE")
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        // Set data to views
        titleTextView.text = title
        synopsisTextView.text = synopsis
        scoreTextView.text = "Score: $score"

        Picasso.get().load(imageUrl).into(imageView)
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
