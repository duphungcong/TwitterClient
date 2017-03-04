package com.duphungcong.twitterclient.ultis;

import com.duphungcong.twitterclient.models.Tweet;

import java.util.ArrayList;

/**
 * Created by udcun on 3/4/2017.
 */

public class ArrayListUlti {
    public static String getLastItemId(ArrayList<Tweet> array) {
        int lastIndex = array.size() - 1;
        return array.get(lastIndex).getmId();
    }
}
