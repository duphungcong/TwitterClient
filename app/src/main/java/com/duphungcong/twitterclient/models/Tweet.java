package com.duphungcong.twitterclient.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by udcun on 3/3/2017.
 */

public class Tweet {
    private User mUser;
    private String mText;
    private String mCreatedAt;

    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();

        try {
            tweet.mText = object.getString("text");
            tweet.mCreatedAt = Tweet.getRelativeTimeAgo(object.getString("created_at"));
            tweet.mUser = User.fromJson(object.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray array) {
        JSONObject tweetJson;
        JSONObject retweetJson;
        ArrayList<Tweet> tweets = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                tweetJson = array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            try {
                retweetJson = tweetJson.getJSONObject("retweeted_status");
            } catch (JSONException e) {
                e.printStackTrace();

                Tweet tweet = Tweet.fromJson(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }

                continue;
            }


            Tweet retweet = Tweet.fromJson(retweetJson);
            if (retweet != null) {
                tweets.add(retweet);
            }
        }

        return tweets;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
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
