package com.duphungcong.twitterclient.models;

/**
 * Created by udcun on 3/3/2017.
 */

public class Tweet {
    private String avatarUrl;
    private String name;
    private String username;
    private String body;

    public Tweet(String avatarUrl, String name, String username, String body) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.username = username;
        this.body = body;
    }

    public Tweet() {
        this.avatarUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQEW70_fdFz4oKi5Fb9pCSOWAH7YreISn9QToz8KMaZIREC4t6Vbk3sJ6U";
        this.name = "duphungcong";
        this.username = "@udcun";
        this.body = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
