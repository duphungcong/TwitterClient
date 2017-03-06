package com.duphungcong.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by udcun on 3/6/2017.
 */

public class Media {
    private String type;
    private String mediaUrl;

    public static Media fromJson(JSONObject object) {
        Media media = new Media();

        try {
            media.type = object.getString("type");
            media.mediaUrl = object.getString("media_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return media;
    }

    public static ArrayList<Media> fromJson(JSONArray array) {
        JSONObject mediaJson;
        ArrayList<Media> medias = new ArrayList<>();

        for (int i = 0; i <  array.length(); i++) {
            try {
                mediaJson = array.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            Media media = Media.fromJson(mediaJson);
            if (media != null) {
                medias.add(media);
            }
        }

        return medias;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
