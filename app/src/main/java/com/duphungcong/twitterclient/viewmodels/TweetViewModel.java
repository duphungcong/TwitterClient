package com.duphungcong.twitterclient.viewmodels;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.format.DateUtils;
import android.widget.ImageView;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by udcun on 3/10/2017.
 */

public class TweetViewModel extends BaseObservable {
    private Tweet mTweet;
    private Context mContext;

    public TweetViewModel(Tweet tweet, Context context) {
        this.mTweet = tweet;
        this.mContext = context;
    }

    public String getName() {
        return mTweet.getUser().getName();
    }

    public String getScreenName() {
        return "@" + mTweet.getUser().getScreenName();
    }

    public String getCreatedAt() {
        return TweetViewModel.getRelativeTimeAgo(mTweet.getCreatedAt());
    }

    public String getBody() {
        return mTweet.getText();
    }

    public String getProfileImageUrl() {
        return mTweet.getUser().getProfileImageUrl();
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

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    // Thanks to https://gist.github.com/nesquena
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
