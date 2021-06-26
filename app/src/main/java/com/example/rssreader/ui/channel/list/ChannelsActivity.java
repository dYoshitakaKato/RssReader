package com.example.rssreader.ui.channel.list;

import androidx.appcompat.app.AppCompatActivity;
import dagger.hilt.android.AndroidEntryPoint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rssreader.R;

@AndroidEntryPoint
public class ChannelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channels_activity);
        int categoryId = getIntent().getIntExtra(EXTRA_KEY, 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ChannelFragment.newInstance(categoryId))
                    .commitNow();
        }
    }

    private static String EXTRA_KEY = "category-id";
    public static void transition(Activity activity, int cateogryId) {
        Intent intent = new Intent(activity, ChannelsActivity.class);
        intent.putExtra(EXTRA_KEY, cateogryId);
        activity.startActivity(intent);
    }
}