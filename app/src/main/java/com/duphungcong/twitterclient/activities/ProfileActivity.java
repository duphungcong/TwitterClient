package com.duphungcong.twitterclient.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.adapters.ProfileTabAdapter;
import com.duphungcong.twitterclient.databinding.ActivityProfileBinding;
import com.duphungcong.twitterclient.models.User;
import com.duphungcong.twitterclient.viewmodels.ProfileViewModel;

/**
 * Created by udcun on 3/11/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private User selectedUser;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(ProfileActivity.this, R.layout.activity_profile);

        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        selectedUser = (User) getIntent().getSerializableExtra("currentUser");
        binding.setVm(new ProfileViewModel(selectedUser, ProfileActivity.this));

        // Get the ViewPager and set it's PageAdapter so that it can display item
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new ProfileTabAdapter(getSupportFragmentManager(), ProfileActivity.this, selectedUser));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}