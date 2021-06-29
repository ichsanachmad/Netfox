package com.aster.netfox.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aster.netfox.R
import com.aster.netfox.data.model.Movie
import com.aster.netfox.data.resource.Resource
import com.aster.netfox.databinding.ActivityMainBinding
import com.aster.netfox.ui.detail.DetailActivity
import com.aster.netfox.utilities.adapter.movie.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieAdapter.OnItemClickListener {
    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject
    lateinit var movieAdapter: MovieAdapter

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = movieAdapter
        }
        movieAdapter.listener = this
        lifecycleScope.launch {
            viewModel.movieListUiState.collect {
                when (it) {
                    is Resource.Loading -> Toast.makeText(
                        applicationContext,
                        "Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                    is Resource.Success -> {
                        Log.d(TAG, "onCreate: ${it.data}")
                        movieAdapter.submitList(it.data)
                    }
                    is Resource.Error -> Log.e(TAG, "onCreate: ${it.message}")
                }
            }
        }
    }

    override fun onItemClick(movie: Movie) {
        Log.d(TAG, "onItemClick: $movie")
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_MOVIE, movie)
            startActivity(this)
        }
    }
}