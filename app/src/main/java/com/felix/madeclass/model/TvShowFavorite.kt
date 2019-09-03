package com.felix.madeclass.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_table")
class TvShowFavorite{

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    var tvId: String? = null
    var photoHigh: String? = null
    var photoLow: String? = null
    var photoBackdropHigh: String? = null
    var photoBackdropLow: String? = null
    var title: String? = null
    var releaseDate: String? = null
    var rating: String? = null
    var overview: String? = null
    var episodes: String? = null
    var seasons: String? = null


}
