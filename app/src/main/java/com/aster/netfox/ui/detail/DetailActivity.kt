package com.aster.netfox.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aster.netfox.data.model.Movie
import com.aster.netfox.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_MOVIE = "extra_movie"
    }
    private var movie: Movie? = null

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        movie = intent.getParcelableExtra(EXTRA_MOVIE)

        movie?.let {
            binding.apply {
                imgCover.load("https://image.tmdb.org/t/p/w500${it.poster}")
                tvTitle.text = it.title
                tvOverview.text = it.overview
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}