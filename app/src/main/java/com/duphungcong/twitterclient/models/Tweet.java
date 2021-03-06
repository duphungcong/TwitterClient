package com.duphungcong.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by udcun on 3/3/2017.
 */

public class Tweet {
    private String id;
    private User user;
    private String text;
    private String createdAt;
    private ArrayList<Media> medias;

    public static Tweet fromJson(JSONObject object) {
        Tweet tweet = new Tweet();

        try {
            tweet.id = object.getString("id_str");
            tweet.text = object.getString("text");
            tweet.createdAt = object.getString("created_at");
            tweet.user = User.fromJson(object.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        try {
            tweet.medias = Media.fromJson(object.getJSONObject("entities").getJSONArray("media"));
        } catch (JSONException e) {
            e.printStackTrace();
            tweet.medias = null;
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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }
}
