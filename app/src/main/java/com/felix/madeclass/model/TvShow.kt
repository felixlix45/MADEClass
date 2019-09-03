package com.felix.madeclass.model

import android.os.Parcel
import android.os.Parcelable

class TvShow() : Parcelable {

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

    constructor(parcel: Parcel) : this() {
        tvId = parcel.readString()
        photoHigh = parcel.readString()
        photoLow = parcel.readString()
        photoBackdropHigh = parcel.readString()
        photoBackdropLow = parcel.readString()
        title = parcel.readString()
        releaseDate = parcel.readString()
        rating = parcel.readString()
        overview = parcel.readString()
        episodes = parcel.readString()
        seasons = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tvId)
        parcel.writeString(photoHigh)
        parcel.writeString(photoLow)
        parcel.writeString(photoBackdropHigh)
        parcel.writeString(photoBackdropLow)
        parcel.writeString(title)
        parcel.writeString(releaseDate)
        parcel.writeString(rating)
        parcel.writeString(overview)
        parcel.writeString(episodes)
        parcel.writeString(seasons)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvShow> {
        override fun createFromParcel(parcel: Parcel): TvShow {
            return TvShow(parcel)
        }

        override fun newArray(size: Int): Array<TvShow?> {
            return arrayOfNulls(size)
        }
    }


}
