package com.example.popularmovies2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>
{

    private ArrayList<Movie> movieData;
    final private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(ListItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView movieImageView;

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        MovieAdapterViewHolder(View view){
            super(view);
            movieImageView = view.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);

        }

    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder mAdapterViewHolder, int position) {
        Movie movie = movieData.get(position);
        Picasso.get().load(movie.getPoster()).into(mAdapterViewHolder.movieImageView);


    }
    public List<Movie> getTasks() {
        return movieData;
    }

    @Override
    public int getItemCount() {

        if (null == movieData) return 0;
        return movieData.size();

    }

    public void setMovieData(ArrayList<Movie> mData) {
        movieData = mData;
        notifyDataSetChanged();

    }


    }



