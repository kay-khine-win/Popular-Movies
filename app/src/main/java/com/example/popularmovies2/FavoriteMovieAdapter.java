package com.example.popularmovies2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieAdapterViewHolder> {

    private List<Movie> movieData;
    final private FavoriteMovieAdapter.ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void onListItemClickFav(int clickedItemIndex);
    }

    public FavoriteMovieAdapter(FavoriteMovieAdapter.ListItemClickListener listener) {
        this.mOnClickListener = listener;
    }




    public class FavoriteMovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView movieImageView;

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClickFav(clickedPosition);
        }

        FavoriteMovieAdapterViewHolder(View view){
            super(view);
            movieImageView = view.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);

        }

    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.FavoriteMovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_poster_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new FavoriteMovieAdapter.FavoriteMovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.FavoriteMovieAdapterViewHolder mAdapterViewHolder, int position) {
        Movie movie = movieData.get(position);
        Picasso.get().load(movie.getPoster()).into(mAdapterViewHolder.movieImageView);

    }

    @Override
    public int getItemCount() {

        if (null == movieData) return 0;
        return movieData.size();

    }


    public void setMovieData(List<Movie> mData) {
            movieData = mData;
            notifyDataSetChanged();

    }


}



