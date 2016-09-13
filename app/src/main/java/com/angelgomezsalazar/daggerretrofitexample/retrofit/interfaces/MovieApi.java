package com.angelgomezsalazar.daggerretrofitexample.retrofit.interfaces;

import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.GenreResponse;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by angelgomez on 8/18/16.
 */
public interface MovieApi {

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreResponse> getGenreList(@Query("api_key") String apiKey);
}
