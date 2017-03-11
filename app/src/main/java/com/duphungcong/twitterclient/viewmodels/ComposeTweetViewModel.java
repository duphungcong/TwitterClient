package com.duphungcong.twitterclient.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.models.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by udcun on 3/11/2017.
 */

public class ComposeTweetViewModel extends BaseObservable {
    private User mUser;
    private String mStatus;
    private Context mContext;

    public ComposeTweetViewModel(User user, Context context) {
        this.mUser = user;
        this.mContext = context;
        this.mStatus = null;
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

    @BindingAdapter({"abc"})
    public static void pushText(TextView view, String text) {
        view.setText(text);
    }
}
