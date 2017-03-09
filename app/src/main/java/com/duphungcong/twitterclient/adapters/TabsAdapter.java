package com.duphungcong.twitterclient.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duphungcong.twitterclient.MentionFragment;
import com.duphungcong.twitterclient.TimelineFragment;

/**
 * Created by udcun on 3/8/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitle[] = new String[] { "HOME", "MENTIONS" };
    private Context context;
    private FragmentManager fragmentManager;

    public TabsAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TimelineFragment();
            case 1:
                return new MentionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}