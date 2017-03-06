package com.duphungcong.twitterclient;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.duphungcong.twitterclient.databinding.AcitvityComposeBinding;
import com.duphungcong.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private AcitvityComposeBinding binding;
    private int remainCharacter = 140;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.acitvity_compose);
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle(R.string.compose_title);
        setSupportActionBar(toolbar);

        bindView();

        binding.etStatus.addTextChangedListener(statusWatcher);

    }

    public void bindView() {
        User currentUser = (User) getIntent().getSerializableExtra("currentUser");

        binding.setUser(currentUser);
        binding.btnCancel.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel :
                finish();
                return;
            case R.id.btnSubmit :
                if (remainCharacter >= 0) {
                    submitTweet(binding.etStatus.getText().toString());
                    finish();
                } else {
                    Toast.makeText(v.getContext(), "Exceed number of words allowed", Toast.LENGTH_SHORT).show();
                }
                return;
            default:
                return;
        }
    }

    public void submitTweet(String status) {
        TwitterClient client = TwitterApplication.getRestClient();

        client.updateStatuses(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                System.out.println(errorResponse.toString());
            }
        });
    }

    private final TextWatcher statusWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            remainCharacter = 140 - s.length();
            binding.tvRemainCharacter.setText(Integer.toString(remainCharacter));
        }
    };
}
