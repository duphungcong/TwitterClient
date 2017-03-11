package com.duphungcong.twitterclient.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;

import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.models.User;

import java.util.ArrayList;

/**
 * Created by udcun on 3/11/2017.
 */

public class ProfileViewModel extends BaseObservable {
    private User mUser;
    private ArrayList<Tweet> mTweets;
    private Context mContext;

    public ProfileViewModel(User user, Context context) {
        this.mUser = user;
        this.mContext = context;
    }
}
