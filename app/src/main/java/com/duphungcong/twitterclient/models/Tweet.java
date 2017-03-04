package com.duphungcong.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by udcun on 3/3/2017.
 */

public class Tweet {
    private User mUser;
    private String mText;

    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();

        try {
            tweet.mText = object.getString("text");
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
}
