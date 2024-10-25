package com.group18.androidproject1.ui.screens.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.group18.androidproject1.R
import com.group18.androidproject1.ui.screens.about.AboutActivity
import com.group18.androidproject1.ui.screens.animes.AnimesActivity
import com.group18.androidproject1.ui.screens.manga.MangaActivity
import com.group18.androidproject1.ui.screens.reviews.ReviewsActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnTopAnimes = findViewById<Button>(R.id.btnAnimes)
        val btnTopManga = findViewById<Button>(R.id.btnManga)
        val btnReviews = findViewById<Button>(R.id.btnReviews)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        btnTopAnimes.setOnClickListener {
            val intent = Intent(this, AnimesActivity::class.java)
            startActivity(intent)
        }

        btnTopManga.setOnClickListener {
            val intent = Intent(this, MangaActivity::class.java)
            startActivity(intent)
        }

        btnReviews.setOnClickListener {
            val intent = Intent(this, ReviewsActivity::class.java)
            startActivity(intent)
        }
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
