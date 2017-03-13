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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.TwitterApplication;
import com.duphungcong.twitterclient.TwitterClient;
import com.duphungcong.twitterclient.activities.ProfileActivity;
import com.duphungcong.twitterclient.adapters.UsersAdapter;
import com.duphungcong.twitterclient.models.User;
import com.duphungcong.twitterclient.ultis.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by udcun on 3/12/2017.
 */

public class FollowerFragment extends Fragment {
    private Context context;
    private ProfileActivity listener;

    private RecyclerView rvUsers;
    private UsersAdapter adapter;
    private List<User> users;

    private EndlessRecyclerViewScrollListener scrollListener;
    private SwipeRefreshLayout swipeContainer;

    private String cursor = "0";
    private User currentUser;

    private final int REQUEST_CODE = 99;

    public FollowerFragment() {
        super();
    }

    public static FollowerFragment newInstance(User user) {
        FollowerFragment fragment = new FollowerFragment();
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (ProfileActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        context = getContext();
        users = new ArrayList<>();
        adapter = new UsersAdapter(context, users);

        currentUser = (User) getArguments().getSerializable("user");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follower, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        rvUsers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rvUsers.setLayoutManager(layoutManager);

        fetchUsers();

        // Listen scroll with infinite pagination
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchUsers();
            }
        };

        rvUsers.addOnScrollListener(scrollListener);

        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                User user = users.get(position);
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("currentUser", user);
                startActivity(intent);
            }
        });

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

    public void fetchUsers() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getFollowers(currentUser.getId(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                ArrayList<User> loadedUsers = User.fromJson(response);
                //cursor = ArrayListUlti.getLastItemId(loadedUsers);
                users.addAll(loadedUsers);
                adapter.notifyDataSetChanged();
                Log.v("MSG", "NUMBER USERS:" + adapter.getItemCount());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(errorResponse.toString());
                Toast.makeText(context, errorResponse.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshTimeline() {
        cursor = null;
        users.clear();
        adapter.notifyDataSetChanged();
        fetchUsers();
        swipeContainer.setRefreshing(false);
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshTimeline();
        }
    };
}