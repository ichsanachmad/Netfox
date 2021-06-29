package com.aster.netfox.utilities.adapter.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aster.netfox.data.model.Movie
import com.aster.netfox.databinding.ListItemMovieBinding
import javax.inject.Inject

class MovieAdapter @Inject constructor() : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val movies = mutableListOf<Movie>()
    var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun submitList(movies: List<Movie>) {
        val diff = DiffUtil.calculateDiff(MovieDiffUtil(this.movies, movies))
        this.movies.clear()
        this.movies.addAll(movies)
        diff.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val view: ListItemMovieBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(movie: Movie) {
            view.apply {
                imgCover.load("https://image.tmdb.org/t/p/w200${movie.poster}")
                tvTitle.text = movie.title
                tvOverview.text = movie.overview
                root.setOnClickListener {
                    listener?.onItemClick(movie)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }
}