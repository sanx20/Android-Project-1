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
import com.google.firebase.auth.FirebaseAuth
import com.group18.androidproject1.MainActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()

        val btnTopAnimes = findViewById<Button>(R.id.btnAnimes)
        val btnTopManga = findViewById<Button>(R.id.btnManga)
        val btnReviews = findViewById<Button>(R.id.btnReviews)
        val btnAbout = findViewById<Button>(R.id.btnAbout)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnTopAnimes.setOnClickListener {
            startActivity(Intent(this, AnimesActivity::class.java))
        }

        btnTopManga.setOnClickListener {
            startActivity(Intent(this, MangaActivity::class.java))
        }

        btnReviews.setOnClickListener {
            startActivity(Intent(this, ReviewsActivity::class.java))
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
