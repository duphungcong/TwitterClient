package com.duphungcong.twitterclient.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.TwitterApplication;
import com.duphungcong.twitterclient.TwitterClient;
import com.duphungcong.twitterclient.activities.ComposeActivity;
import com.duphungcong.twitterclient.activities.LoginActivity;
import com.duphungcong.twitterclient.activities.MainActivity;
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
 * Created by udcun on 3/8/2017.
 */

public class MentionFragment extends Fragment {

    private Context context;
    private MainActivity listener;

    private RecyclerView rvTweets;
    private TweetsAdapter adapter;
    private List<Tweet> tweets;

    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;

    private String maxId;
    private User currentUser;

    private final int REQUEST_CODE = 98;

    public MentionFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (MainActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        context = getContext();
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(context, tweets);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mention, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTweets = (RecyclerView) view.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
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
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(onRefreshListener);
        swipeContainer.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCompose:
                if (currentUser!= null) {
                    Intent intent = new Intent(context, ComposeActivity.class);
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
                Intent i = new Intent(context, LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void fetchTweets() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getMentions(maxId, new JsonHttpResponseHandler() {
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
                Toast.makeText(context, errorResponse.toString(), Toast.LENGTH_SHORT).show();
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
