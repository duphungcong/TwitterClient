package com.duphungcong.twitterclient.ultis;

import com.duphungcong.twitterclient.models.Tweet;

import java.util.ArrayList;

/**
 * Created by udcun on 3/4/2017.
 */

public class ArrayListUlti {
    public static String getLastItemId(ArrayList<Tweet> array) {
        String id = null;
        if (array != null && array.size() != 0) {
            int lastIndex = array.size() - 1;
            Tweet tweet = array.get(lastIndex);
            id = tweet.getmId();
        }
        return id;
    }
}
