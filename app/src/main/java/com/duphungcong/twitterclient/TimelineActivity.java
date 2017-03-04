package com.duphungcong.twitterclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.duphungcong.twitterclient.adapters.TweetsAdapter;
import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.ultis.ArrayListUlti;
import com.duphungcong.twitterclient.ultis.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by udcun on 3/3/2017.
 */

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    private EndlessRecyclerViewScrollListener scrollListener;
    private String maxId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();

        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);

        //fetchTweets("836278419038011393");
        fetchTweets();

        // Listen scroll with infinite pagination
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchTweets();
            }
        };

        rvTweets.addOnScrollListener(scrollListener);
    }

    public void fetchTweets() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<Tweet> loadedTweets = Tweet.fromJson(response);
                maxId = ArrayListUlti.getLastItemId(loadedTweets);
                tweets.addAll(loadedTweets);
                adapter.notifyDataSetChanged();
                //Log.v("MSG", "NUMBER TWEETS:" + adapter.getItemCount());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(errorResponse.toString());
                Toast.makeText(TimelineActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
