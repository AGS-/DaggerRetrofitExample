package com.angelgomezsalazar.daggerretrofitexample.retrofit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by angelgomez on 8/18/16.
 */
public class Movie {

    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("release_date")
    private String releaseDate;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }
}
