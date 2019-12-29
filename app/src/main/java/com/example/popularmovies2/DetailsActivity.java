package com.example.popularmovies2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class DetailsActivity extends AppCompatActivity {

    TextView title,released_date,rate, plot;
    TextView reviewLabel,reviewAuthorLabel,review1,review2;
    ImageView poster_iv,videoView;

    Button fav_btn;
    Movie movie;
    private MovieRoomDatabase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDb = MovieRoomDatabase.getInstance(getApplicationContext());

        title = findViewById(R.id.movie_title);
        released_date = findViewById(R.id.released_date);
        poster_iv = findViewById(R.id.img_poster);
        rate = findViewById(R.id.rate);
        plot = findViewById(R.id.movie_overview);
        fav_btn= findViewById(R.id.fav_btn);
        videoView = findViewById(R.id.video_image);
        review1 = findViewById(R.id.review1);
        review2 = findViewById(R.id.review2);
        reviewLabel = findViewById(R.id.reviewLabel);
        reviewAuthorLabel = findViewById(R.id.reviewAuthor);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        movie = (Movie) bundle.getSerializable("KEY");

        title.setText(movie.getTitle());
        released_date.setText(movie.getDate());
        rate.setText(movie.getVote());
        plot.setText(movie.getPlot());
        review1.setText(movie.getReview1());
        review2.setText(movie.getReview2());


        Picasso.get()
                 .load(movie.getPoster())
                 .into(poster_iv);

    }

    public void playTrailer(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movie.getVideo_key() ));
        startActivity(intent);
    }

    public void saveDb(View view) {

        final int id = movie.getMovieID();

           final Movie newMovie = new Movie(movie.getMovieID(), movie.getTitle(), movie.getDate(), movie.getPoster(), movie.getVote(), movie.getPlot(), movie.getVideo_key(), movie.getReview1(), movie.getReview2());
           AppExecutors.getInstance().diskIO().execute(new Runnable() {
               @Override
               public void run() {
                   Movie oldMovie = mDb.movieDao().loadMoviekById(id);
                   if (oldMovie!=null){
                       mDb.movieDao().deleteTask(oldMovie);
                       finish();
                   }else {
                       mDb.movieDao().insert(newMovie);
                       finish();
                   }
               }
           });
    }
}
