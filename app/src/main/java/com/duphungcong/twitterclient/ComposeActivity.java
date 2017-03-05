package com.duphungcong.twitterclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.duphungcong.twitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvName, tvScreenName, tvRemainCharacter;
    private ImageView ivAvatar;
    private EditText etStatus;
    private Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_compose);
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle(R.string.compose_title);
        setSupportActionBar(toolbar);

        bindView();

    }

    public void bindView() {
        User currentUser = (User) getIntent().getSerializableExtra("currentUser");

        tvName = (TextView) findViewById(R.id.tvName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvRemainCharacter = (TextView) findViewById(R.id.tvRemainCharacter);
        etStatus = (EditText) findViewById(R.id.etStatus);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        tvName.setText(currentUser.getName());
        tvScreenName.setText("@" + currentUser.getScreenName());
        Picasso.with(this)
                .load(currentUser.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(10, 3))
                .fit()
                .placeholder(R.drawable.image_loading)
                .into(ivAvatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel :
                finish();
                return;
            case R.id.btnSubmit :
                submitTweet(etStatus.getText().toString());
                finish();
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
}
