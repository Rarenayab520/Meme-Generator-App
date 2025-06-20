package com.nayab.memegeneratorapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nayab.memegeneratorapp.R
import com.nayab.memegeneratorapp.data.RedditMeme
import com.nayab.memegeneratorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupClickListeners()

        // Load first meme
        viewModel.loadRandomMeme()
    }

    private fun setupObservers() {
        viewModel.memeData.observe(this) { meme ->
            if (meme != null) {
                binding.memeTitle.text = meme.title
                Glide.with(this)
                    .load(meme.url)
                    .placeholder(R.drawable.meme_placeholder)
                    .into(binding.memeImage)
            } else {
                Toast.makeText(this, "Failed to load meme", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.loadingContainer.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnNext.isEnabled = !isLoading
        }
    }

    private fun setupClickListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.loadRandomMeme()
        }

        binding.btnShare.setOnClickListener {
            shareMeme()
        }

        binding.btnLike.setOnClickListener {
            toggleLike()
        }
    }

    private fun shareMeme() {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check out this meme: ${viewModel.memeData.value?.title}")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share meme via"))
    }

    private fun toggleLike() {
        val isLiked = viewModel.toggleLike()
        val icon = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        binding.btnLike.icon = ContextCompat.getDrawable(this, icon)
    }
}
