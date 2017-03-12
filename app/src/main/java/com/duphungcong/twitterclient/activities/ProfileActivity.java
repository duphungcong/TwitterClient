package com.duphungcong.twitterclient.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.TwitterApplication;
import com.duphungcong.twitterclient.TwitterClient;
import com.duphungcong.twitterclient.adapters.TweetsAdapter;
import com.duphungcong.twitterclient.databinding.ActivityProfileBinding;
import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.models.User;
import com.duphungcong.twitterclient.ultis.ArrayListUlti;
import com.duphungcong.twitterclient.ultis.EndlessRecyclerViewScrollListener;
import com.duphungcong.twitterclient.viewmodels.ProfileViewModel;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by udcun on 3/11/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;

    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;

    private String maxId;
    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(ProfileActivity.this, tweets);

        bindView();
    }

    public void bindView() {
        currentUser = (User) getIntent().getSerializableExtra("currentUser");

        binding.setVm(new ProfileViewModel(currentUser, getApplicationContext()));

        //rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        binding.rvTweets.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProfileActivity.this);
        binding.rvTweets.setLayoutManager(layoutManager);

        fetchTweets();

        // Listen scroll with infinite pagination
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchTweets();
            }
        };

        binding.rvTweets.addOnScrollListener(scrollListener);

        //getCurrentUser();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(onRefreshListener);
        swipeContainer.setColorSchemeResources(R.color.colorAccent);
    }

    public void fetchTweets() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getUserTimeline(currentUser.getId(), maxId, new JsonHttpResponseHandler() {
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
                Toast.makeText(ProfileActivity.this, errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCurrentUser() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getVerifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                currentUser = User.fromJson(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void refreshTimeline() {
        maxId = null;
        tweets.clear();
        adapter.notifyDataSetChanged();
        fetchTweets();
        swipeContainer.setRefreshing(false);
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshTimeline();
        }
    };
}
