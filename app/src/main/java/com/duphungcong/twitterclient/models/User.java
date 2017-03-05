package com.duphungcong.twitterclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by udcun on 3/3/2017.
 */

public class User implements Serializable {
    private String name;
    private String screenName;
    private String profileImageUrl;

    public static User fromJson(JSONObject object) {
        User user = new User();

        try {
            user.name = object.getString("name");
            user.screenName = object.getString("screen_name");
            user.profileImageUrl = object.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return user;
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
}
