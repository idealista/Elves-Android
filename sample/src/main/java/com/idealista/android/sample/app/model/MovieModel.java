package com.idealista.android.sample.app.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.idealista.android.mvp.View;

public class MovieModel implements View, Parcelable {

    private String title;

    public MovieModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    protected MovieModel(Parcel in) {
        this.title = in.readString();
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
