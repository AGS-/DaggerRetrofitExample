package com.angelgomezsalazar.daggerretrofitexample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.angelgomezsalazar.daggerretrofitexample.MyApplication;
import com.angelgomezsalazar.daggerretrofitexample.R;
import com.angelgomezsalazar.daggerretrofitexample.adapters.MovieRecyclerAdapter;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.interfaces.MovieApi;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.Genre;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.GenreResponse;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.Movie;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.MovieResponse;
import com.angelgomezsalazar.daggerretrofitexample.utils.Api;
import com.angelgomezsalazar.daggerretrofitexample.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList;

    @BindView(R.id.main_recycler_view) RecyclerView movieRecyclerView;
    private RecyclerView.Adapter movieAdapter;

    private MovieApi movieApi;
    @Inject
    Retrofit retrofit;

    public static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((MyApplication) getApplication()).getNetComponent().inject(this);

        movieApi = retrofit.create(MovieApi.class);

        movieList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieRecyclerView.setLayoutManager(linearLayoutManager);
        movieRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(
                linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.d(TAG, "On Load More Called");
                if (movieList.size() < 50) {
                    callUpcomingMovieApi(currentPage);
                }
            }
        });
        initialLoad();
    }

    private void callUpcomingMovieApi(int page) {
        // TODO: Put your own api key here
        Call<MovieResponse> upcomingMovieCall =
                movieApi.getUpcomingMovies(page, Api.KEY);
        upcomingMovieCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movieList.addAll(response.body().results);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void callGenreApi() {
        MovieApi movieApi = retrofit.create(MovieApi.class);
        // TODO: Put your own api key here
        Call<GenreResponse> genreCall =
                movieApi.getGenreList(Api.KEY);
        genreCall.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                HashMap<Integer, String> genreHashMap = new HashMap<>();
                for (Genre genre: response.body().getGenres()) {
                    genreHashMap.put(genre.getId(), genre.getName());
                }
                movieAdapter = new MovieRecyclerAdapter(MainActivity.this, movieList, genreHashMap);
                movieRecyclerView.setAdapter(movieAdapter);

                callUpcomingMovieApi(1);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {

            }
        });
    }

    // Used for Initial Load and any reloading that may take place
    private void initialLoad() {
        callGenreApi();
    }
}
