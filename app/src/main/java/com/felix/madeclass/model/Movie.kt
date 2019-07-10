package com.felix.madeclass.model

import android.os.Parcel
import android.os.Parcelable

class Movie() : Parcelable {
    var photoHigh: String? = null
    var photoLow: String? = null
    var photoBackdropHigh: String? = null
    var photoBackdropLow: String? = null
    var title: String? = null
    var releaseDate: String? = null
    var rating: String? = null
    var overview: String? = null

    constructor(parcel: Parcel) : this() {
        photoHigh = parcel.readString()
        photoLow = parcel.readString()
        photoBackdropHigh = parcel.readString()
        photoBackdropLow = parcel.readString()
        title = parcel.readString()
        releaseDate = parcel.readString()
        rating = parcel.readString()
        overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(photoHigh)
        parcel.writeString(photoLow)
        parcel.writeString(photoBackdropHigh)
        parcel.writeString(photoBackdropLow)
        parcel.writeString(title)
        parcel.writeString(releaseDate)
        parcel.writeString(rating)
        parcel.writeString(overview)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}
