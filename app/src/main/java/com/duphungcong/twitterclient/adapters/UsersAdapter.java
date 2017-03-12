package com.duphungcong.twitterclient.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duphungcong.twitterclient.R;
import com.duphungcong.twitterclient.databinding.UserItemBinding;
import com.duphungcong.twitterclient.models.User;
import com.duphungcong.twitterclient.viewmodels.ProfileViewModel;

import java.util.List;

/**
 * Created by udcun on 3/12/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private Context context;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public UsersAdapter(Context context, List<User> objects) {
        this.context = context;
        users = objects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProfileViewModel vm = new ProfileViewModel(users.get(position), context);

        holder.binding.setVm(vm);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private UserItemBinding binding;

        public ViewHolder(final View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
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
