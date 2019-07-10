package com.felix.madeclass.model

import android.os.Parcel
import android.os.Parcelable

class TvShow() : Parcelable {
    var photoHigh: String? = null
    var photoLow: String? = null
    var photoBackdropHigh: String? = null
    var photoBackdropLow: String? = null
    var title: String? = null
    var release_date: String? = null
    var rating: String? = null
    var overview: String? = null

    constructor(parcel: Parcel) : this() {
        photoHigh = parcel.readString()
        photoLow = parcel.readString()
        photoBackdropHigh = parcel.readString()
        photoBackdropLow = parcel.readString()
        title = parcel.readString()
        release_date = parcel.readString()
        rating = parcel.readString()
        overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(photoHigh)
        parcel.writeString(photoLow)
        parcel.writeString(photoBackdropHigh)
        parcel.writeString(photoBackdropLow)
        parcel.writeString(title)
        parcel.writeString(release_date)
        parcel.writeString(rating)
        parcel.writeString(overview)
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
