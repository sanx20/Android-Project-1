package com.group18.androidproject1.ui.screens.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.group18.androidproject1.MainActivity
import com.group18.androidproject1.databinding.ActivityMenuBinding
import com.group18.androidproject1.ui.screens.about.AboutActivity
import com.group18.androidproject1.ui.screens.animes.AnimesActivity
import com.group18.androidproject1.ui.screens.manga.MangaActivity
import com.group18.androidproject1.ui.screens.reviews.ReviewsActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnimes.setOnClickListener {
            val intent = Intent(this, AnimesActivity::class.java)
            startActivity(intent)
        }

        binding.btnManga.setOnClickListener {
            val intent = Intent(this, MangaActivity::class.java)
            startActivity(intent)
        }

        binding.btnReviews.setOnClickListener {
            val intent = Intent(this, ReviewsActivity::class.java)
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
