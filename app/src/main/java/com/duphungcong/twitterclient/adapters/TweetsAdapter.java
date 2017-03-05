package com.duphungcong.twitterclient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by udcun on 3/3/2017.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;

    public TweetsAdapter(Context context, List<Tweet> objects) {
        mContext = context;
        mTweets = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tweet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvName.setText(tweet.getmUser().getName());
        holder.tvUsername.setText("@" + tweet.getmUser().getScreenName());
        holder.tvCreatedAt.setText(tweet.getmCreatedAt());
        holder.tvBody.setText(tweet.getmText());

        Picasso.with(mContext)
                .load(tweet.getmUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(10, 3))
                .fit()
                .placeholder(R.drawable.image_loading)
                .into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public Context getContext() {
        return mContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivAvatar;
        public TextView tvName;
        public TextView tvUsername;
        public TextView tvCreatedAt;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);

            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }
    }
}
