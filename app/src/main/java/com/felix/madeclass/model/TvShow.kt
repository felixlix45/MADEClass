package com.felix.madeclass.model

import android.os.Parcel
import android.os.Parcelable

class TvShow() : Parcelable {
    var photo: Int = 0
    var title: String? = null
    var release_date: String? = null
    var rating: String? = null
    var overview: String? = null

    constructor(parcel: Parcel) : this() {
        photo = parcel.readInt()
        title = parcel.readString()
        release_date = parcel.readString()
        rating = parcel.readString()
        overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(photo)
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
