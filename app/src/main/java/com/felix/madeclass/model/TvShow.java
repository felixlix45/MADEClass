package com.felix.madeclass.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {
    private int photo;
    private String title;
    private String release_date;
    private String rating;
    private String overview;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photo);
        dest.writeString(this.title);
        dest.writeString(this.release_date);
        dest.writeString(this.rating);
        dest.writeString(this.overview);
    }

    public TvShow() {
    }

    protected TvShow(Parcel in) {
        this.photo = in.readInt();
        this.title = in.readString();
        this.release_date = in.readString();
        this.rating = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
