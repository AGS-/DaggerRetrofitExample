package com.angelgomezsalazar.daggerretrofitexample.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelgomezsalazar.daggerretrofitexample.R;
import com.angelgomezsalazar.daggerretrofitexample.activities.DetailActivity;
import com.angelgomezsalazar.daggerretrofitexample.retrofit.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by angelgomez on 8/18/16.
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<Movie> movieList;
    private HashMap<Integer, String> genreHashMap;

    private final Activity context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_main_card_view) CardView cardView;
        @BindView(R.id.item_main_image_poster) ImageView posterImageView;
        @BindView(R.id.item_main_text_movie_name) TextView nameTextView;
        @BindView(R.id.item_main_text_genre) TextView genreTextView;
        @BindView(R.id.item_main_text_date) TextView dateTextView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public MovieRecyclerAdapter(Activity context, List<Movie> movieList, HashMap<Integer,
            String> genreHashMap) {
        this.movieList = movieList;
        this.genreHashMap = genreHashMap;
        this.context = context;
    }

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movie movie = movieList.get(position);
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .placeholder(R.drawable.sample_movie_poster)
                .into(holder.posterImageView);
        holder.nameTextView.setText(movie.getTitle());
        String genreString = "";
        for (Integer genreId: movie.getGenreIds()) {
            if (genreString.equals("")) {
                genreString += genreHashMap.get(genreId);
            } else {
                genreString += ", " + genreHashMap.get(genreId);
            }
        }
        final String finalGenreString = genreString;
        holder.genreTextView.setText(genreString);
        holder.dateTextView.setText(movie.getReleaseDate());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.NAME_EXTRA, movie.getTitle());
                detailIntent.putExtra(DetailActivity.GENRE_EXTRA, finalGenreString);
                detailIntent.putExtra(DetailActivity.RELEASE_EXTRA, movie.getReleaseDate());
                detailIntent.putExtra(DetailActivity.IMAGE_EXTRA, movie.getPosterPath());
                detailIntent.putExtra(DetailActivity.OVERVIEW_EXTRA, movie.getOverview());
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}
