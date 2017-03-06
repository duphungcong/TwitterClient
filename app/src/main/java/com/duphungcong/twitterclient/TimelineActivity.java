package com.duphungcong.twitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.duphungcong.twitterclient.adapters.TweetsAdapter;
import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.models.User;
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

    private Toolbar toolbar;
    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;
    private String maxId;
    private User currentUser;

    private final int REQUEST_CODE = 99;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();

        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);

        fetchTweets();

        // Listen scroll with infinite pagination
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchTweets();
            }
        };

        rvTweets.addOnScrollListener(scrollListener);

        getCurrentUser();

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(onRefreshListener);
        swipeContainer.setColorSchemeResources(R.color.colorAccent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCompose:
                if (currentUser!= null) {
                    Intent intent = new Intent(TimelineActivity.this, ComposeActivity.class);
                    intent.putExtra("currentUser", currentUser);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                return true;
            case R.id.actionRefresh :
                refreshTimeline();
                return true;
            case R.id.actionLogout :
                TwitterClient client = TwitterApplication.getRestClient();
                client.clearAccessToken();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            refreshTimeline();
        }
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
