package com.yilmazgokhan.movieapp.v1.ui.movie_search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yilmazgokhan.movieapp.v1.adapter.MovieAdapter;
import com.yilmazgokhan.movieapp.v1.data.movie_search.Result;
import com.yilmazgokhan.movieapp.v1.databinding.FragmentMovieSearchBinding;

import java.util.List;

public class MovieSearchFragment extends Fragment {

    private FragmentMovieSearchBinding binding;
    private MovieSearchViewModel mViewModel;
    private MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Prepare View Model
        mViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel.class);

        this.initComponents();
        this.initClicks();
        this.initObservers();
    }

    /**
     * Initialize components & Create necessary adapter
     */
    private void initComponents() {
        //Recycler View initialize
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMovies.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieAdapter(getContext());
        binding.rvMovies.setAdapter(movieAdapter);
    }

    /**
     * Handle RecyclerView's clicks
     */
    private void initClicks() {
        //Search button
        binding.btnSearch.setOnClickListener(v -> mViewModel.search(binding.etSearch.getText().toString()));
        //Adapter's click
        movieAdapter.setOnClickListener((pos, movie) -> {
            NavDirections act = MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailFragment(movie.getId());
            Navigation.findNavController(getView()).navigate(act);
        });
    }

    /**
     * Initialize observers for getting data from the ViewModel
     */
    private void initObservers() {
        mViewModel.getSearchList().observe(getViewLifecycleOwner(), this::prepareRecycler);
        mViewModel.getSearchControl().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                Toast.makeText(getContext(), "You should enter at least one letter", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Set the data to the RecyclerView's adapter
     *
     * @param models as List<Result>
     */
    private void prepareRecycler(List<Result> models) {
        movieAdapter.setAdapterList(models);
    }
}