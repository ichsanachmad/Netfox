package com.aster.netfox.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,

    @SerializedName("poster_path")
    val poster: String,

    val overview: String,

    @SerializedName("vote_average")
    val vote: Double,
):Parcelable
