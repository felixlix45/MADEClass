package com.felix.madeclass.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
class MovieFavorite {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    var movieId: String? = null
    var photoHigh: String? = null
    var photoLow: String? = null
    var photoBackdropHigh: String? = null
    var photoBackdropLow: String? = null
    var title: String? = null
    var releaseDate: String? = null
    var rating: String? = null
    var overview: String? = null
    var adult: Boolean? = null
}
