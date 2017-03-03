package com.duphungcong.twitterclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.duphungcong.twitterclient.adapters.TweetsAdapter;
import com.duphungcong.twitterclient.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by udcun on 3/3/2017.
 */

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);

        tweets = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            tweets.add(new Tweet());
        }

        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setAdapter(adapter);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
    }
}
