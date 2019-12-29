package com.example.popularmovies2;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movie_table")
public class Movie implements Serializable {

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "poster_path")
    private String poster;

    @ColumnInfo(name = "vote")
    private String vote;

    private String video_key;



    private String review1;
    private String review2;



    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int movieID;

    @Ignore
    public Movie(String title, String date, String poster, String vote,String plot, String video_key,String review1,String review2 ) {
        this.title = title;
        this.date = date;
        this.poster = poster;
        this.vote = vote;
        this.video_key = video_key;
        this.plot = plot;
        this.review1 = review1;
        this.review2 = review2;
    }

    public Movie(int id,String title, String date,String poster,String vote,String plot,String video_key,String review1,String review2 ) {
        this.movieID = id;
        this.title = title;
        this.date = date;
        this.poster = poster;
        this.vote = vote;
        this.video_key = video_key;
        this.plot = plot;
        this.review1 = review1;
        this.review2 = review2;
    }

    public Movie(int id,String title, String date,String poster,String vote,String plot,String video_key) {
        this.movieID = id;
        this.title = title;
        this.date = date;
        this.poster = poster;
        this.vote = vote;
        this.video_key = video_key;
        this.plot = plot;

    }

    public int getMovieID() {
        return this.movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getVideo_key() {
        return video_key;
    }

    public void setVideo_key(String video_key) {
        this.video_key = video_key;
    }

    public Movie(String title, String date, String poster, String vote, String plot) {
        this.title = title;
        this.date = date;
        this.vote = vote;
        this.plot = plot;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String  getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    private String plot;

    public String getReview1() {
        return review1;
    }

    public String getReview2() {
        return review2;
    }
    public void setReview1(String review1) {
        this.review1 = review1;
    }

    public void setReview2(String review2) {
        this.review2 = review2;
    }
}

