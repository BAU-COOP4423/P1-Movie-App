package com.yilmazgokhan.movieapp.v1.ui.movie_detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.yilmazgokhan.movieapp.v1.R;
import com.yilmazgokhan.movieapp.v1.data.movie_detail.Genre;
import com.yilmazgokhan.movieapp.v1.data.movie_detail.MovieDetailModel;
import com.yilmazgokhan.movieapp.v1.databinding.FragmentMovieDetailBinding;
import com.yilmazgokhan.movieapp.v1.util.Constants;

public class MovieDetailFragment extends Fragment {

    private FragmentMovieDetailBinding binding;
    private MovieDetailViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Prepare View Model
        mViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        checkArguments();
        initObservers();
    }

    /**
     * Check arguments via SafeArg
     */
    private void checkArguments() {
        int movieId = MovieDetailFragmentArgs.fromBundle(getArguments()).getMovieId();
        if (movieId != 0)
            mViewModel.getMovieDetail(movieId);
    }

    /**
     * Initialize observers for getting data from the ViewModel
     */
    private void initObservers() {
        mViewModel.getMovieDetail().observe(getViewLifecycleOwner(), this::prepareComponents);
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
            Glide.with(getContext())
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