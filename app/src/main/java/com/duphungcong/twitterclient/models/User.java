package com.duphungcong.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by udcun on 3/3/2017.
 */

public class User implements Serializable {
    private String id;
    private String name;
    private String screenName;
    private String profileImageUrl;
    private int followersCount;
    private int friendsCount;

    public static User fromJson(JSONObject object) {
        User user = new User();

        try {
            user.id = object.getString("id_str");
            user.name = object.getString("name");
            user.screenName = object.getString("screen_name");
            user.profileImageUrl = object.getString("profile_image_url");
            user.followersCount = object.getInt("followers_count");
            user.friendsCount = object.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }

    public static ArrayList<User> fromJson(JSONArray array) {
        JSONObject userJson;
        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                userJson = array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }


            User user = User.fromJson(userJson);
            if (user != null) {
                users.add(user);
            }
        }

        return users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
