package com.duphungcong.twitterclient.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

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

    public String getName() {
        return mUser.getName();
    }

    public String getScreenName() {
        return "@" + mUser.getScreenName();
    }

    public String getProfileImageUrl() {
        return mUser.getProfileImageUrl();
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .transform(new RoundedCornersTransformation(10, 3))
                .fit()
                .placeholder(R.drawable.image_loading)
                .into(view);
    }

    public String getFollowers() {
        return mUser.getFollowersCount() + " Followers";
    }

    public String getFollwing() {
        return mUser.getFriendsCount() + " Following";
    }
}
