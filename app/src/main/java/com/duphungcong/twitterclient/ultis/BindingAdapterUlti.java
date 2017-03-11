package com.duphungcong.twitterclient.ultis;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.duphungcong.twitterclient.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by udcun on 3/5/2017.
 */

public class BindingAdapterUlti {
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .transform(new RoundedCornersTransformation(10, 3))
                .fit()
                .placeholder(R.drawable.image_loading)
                .into(view);
    }
}