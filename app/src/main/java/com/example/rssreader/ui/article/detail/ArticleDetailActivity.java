package com.example.rssreader.ui.article.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rssreader.databinding.ActivityArticleDetailBinding;

public class ArticleDetailActivity extends AppCompatActivity {

    private ArticleDetailViewModel mViewModel;
    private ActivityArticleDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArticleDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        String url = getIntent().getStringExtra(EXTRA_KEY);
        mViewModel = new ArticleDetailViewModel(binding, url);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        binding.webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private static String EXTRA_KEY = "url";
    public static void transition(Activity activity, String url) {
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        intent.putExtra(EXTRA_KEY, url);
        activity.startActivity(intent);
    }
}