package com.yilmazgokhan.movieapp.v2.ui.movie_detail;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.yilmazgokhan.movieapp.v2.R;
import com.yilmazgokhan.movieapp.v2.data.movie_detail.Genre;
import com.yilmazgokhan.movieapp.v2.data.movie_detail.MovieDetailModel;
import com.yilmazgokhan.movieapp.v2.databinding.ActivityMovieDetailBinding;
import com.yilmazgokhan.movieapp.v2.util.Constants;

public class MovieDetailActivity extends AppCompatActivity {

    private ActivityMovieDetailBinding binding;
    private MovieDetailViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Prepare View Model
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        checkArguments();
        initObservers();
    }

    /**
     * Check arguments via SafeArg
     */
    private void checkArguments() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int movieId = bundle.getInt(Constants.ARG_MOVIE_ID);
            mViewModel.getMovieDetail(movieId);
        } else finish();
    }

    /**
     * Initialize observers for getting data from the ViewModel
     */
    private void initObservers() {
        mViewModel.getMovieDetail().observe(this, this::prepareComponents);
    }

    /**
     * Set the data to view components
     *
     * @param movie as MovieDetailModel
     */
    private void prepareComponents(MovieDetailModel movie) {
        //Backdrop image
        if (!TextUtils.isEmpty(movie.getBackdropPath())) {
            String posterPath = Constants.BACKDROP_BASE_PATH + movie.getBackdropPath();
            Glide.with(this)
                    .load(posterPath)
                    .placeholder(R.drawable.ic_baseline_broken_image_24)
                    .into(binding.ivBackdrop);
        }
        //Overview
        if (!TextUtils.isEmpty(movie.getOverview()))
            binding.tvOverview.setText(movie.getOverview());
        //Genres / Categories
        if (movie.getGenres() != null & movie.getGenres().size() > 0) {
            String genres = "";
            for (Genre genre : movie.getGenres()) {
                if (genres.equals(""))
                    genres = genre.getName();
                else genres += ", " + genre.getName();
            }
            binding.tvCategory.setText(genres);
        }
        //Release Date
        if (!TextUtils.isEmpty(movie.getReleaseDate()))
            binding.tvDate.setText(movie.getReleaseDate());
        //Score
        if (movie.getVoteAverage() != 0)
            binding.tvScore.setText(movie.getVoteAverage().toString());
    }
}