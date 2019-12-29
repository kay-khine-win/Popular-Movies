package com.example.popularmovies2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static ArrayList<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

       ArrayList<Movie> movies = extractMovieFromJson(jsonResponse);
        return movies;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;

    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


    private static ArrayList<Movie> extractMovieFromJson(String movieJSON) {
        ArrayList<Movie> movies = new ArrayList<>();

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            if (resultsArray.length() > 0) {
                ArrayList<String> reviews = new ArrayList<>();
                String review1;
                String review2;

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject movieObj = resultsArray.getJSONObject(i);

                    String title = movieObj.getString("title");
                    String date = movieObj.getString("release_date");
                    String vote = movieObj.getString("vote_average");
                    String poster = "https://image.tmdb.org/t/p/w185/" + movieObj.getString("poster_path");
                    String plot = movieObj.getString("overview");
                    int movie_id = movieObj.getInt("id");

                    String video_info = "https://api.themoviedb.org/3/movie/"+ movie_id +"/videos?api_key=ced27a643bee0aa606584e66eb6584b5";
                    String video_key = extractVideopath(video_info);

                    String reviewPath = "https://api.themoviedb.org/3/movie/"+ movie_id +"/reviews?api_key=ced27a643bee0aa606584e66eb6584b5";
                    String path = extractReviewpath(reviewPath);
                    reviews = extractReview(path);
                    if(reviews != null) {
                    review1 = reviews.get(0).toString();
                    review2 = reviews.get(1).toString();
                    }
                    else{
                       review1 = null;
                        review2 = null;

                    }

                    //return new Movie(title, date, poster,vote,plot);

                    Movie movie = new Movie(movie_id, title, date, poster, vote, plot,video_key,review1,review2);
                    movies.add(movie);


                }

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        Log.d("RETURN MOVIE",movies.toString());
        return movies;
    }


    private static String extractVideopath(String video_info) {
        URL url = createUrl(video_info);
        String jsonVideoinfo = null;
        try {
            jsonVideoinfo = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        String key = extractKey(jsonVideoinfo);

        return key;
    }

    private static String extractKey(String jsonVideoinfo) {
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonVideoinfo);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            if (resultsArray.length() > 0) {

                JSONObject movieObj = resultsArray.getJSONObject(0);
                String key = movieObj.getString("key");
                return key;
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

    private static String extractReviewpath(String jsonReview) {
        URL url = createUrl(jsonReview);
        String jsonReviewurl = null;
        try {
            jsonReviewurl = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        extractReview(jsonReviewurl);
        return jsonReviewurl;
    }

    private static ArrayList<String> extractReview(String jsonReview) {

            try {
            ArrayList<String> reviewArrayList = new ArrayList<String>();

            JSONObject baseJsonResponse = new JSONObject(jsonReview);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            if (resultsArray.length() > 0) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject reviewObj = resultsArray.getJSONObject(i);
                    String author = reviewObj.getString("author");
                    String content = reviewObj.getString("content");
                    reviewArrayList.add(author);
                    reviewArrayList.add(content);

                }
                return reviewArrayList;

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

}
