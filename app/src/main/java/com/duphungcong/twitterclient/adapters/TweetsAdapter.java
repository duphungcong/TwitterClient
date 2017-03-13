package com.duphungcong.twitterclient.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.databinding.TweetItemBinding;
import com.duphungcong.twitterclient.models.Tweet;
import com.duphungcong.twitterclient.viewmodels.TweetViewModel;

import java.util.List;

/**
 * Created by udcun on 3/3/2017.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private List<Tweet> tweets;
    private Context context;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TweetsAdapter(Context context, List<Tweet> objects) {
        this.context = context;
        tweets = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tweet_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TweetViewModel vm = new TweetViewModel(tweets.get(position), context);

        holder.binding.setTweetViewModel(vm);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TweetItemBinding binding;

        public ViewHolder(final View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

            binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }
}
