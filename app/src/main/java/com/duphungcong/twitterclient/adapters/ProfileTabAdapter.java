package com.duphungcong.twitterclient.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.duphungcong.twitterclient.fragments.FollowerFragment;
import com.duphungcong.twitterclient.fragments.UserTimelineFragment;
import com.duphungcong.twitterclient.models.User;

/**
 * Created by udcun on 3/12/2017.
 */

public class ProfileTabAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitle[] = new String[] { "TWEETS", "FOLLOWERS" };
    private Context context;
    private User selectedUser;

    public ProfileTabAdapter(FragmentManager fm, Context context, User user) {
        super(fm);
        this.context = context;
        this.selectedUser = user;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return UserTimelineFragment.newInstance(selectedUser);
            case 1:
                return FollowerFragment.newInstance(selectedUser);
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
