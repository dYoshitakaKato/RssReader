package com.example.rssreader.util;

import android.widget.ImageView;

import com.example.rssreader.R;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;
import kotlin.jvm.JvmStatic;

public class ImageUtil {
    @JvmStatic
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (url == null) {
            return;
        }
        if (url.equals("")) {
            return;
        }

        Picasso.get().load(url).error(R.drawable.ic_baseline_no_photography_24).into(view);
    }
}
