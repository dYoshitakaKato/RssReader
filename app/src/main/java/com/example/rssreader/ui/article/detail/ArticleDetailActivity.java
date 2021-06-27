package com.example.rssreader.ui.article.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.rssreader.databinding.ActivityArticleDetailBinding;

import java.util.ArrayList;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

public class ArticleDetailActivity extends FragmentActivity {

    private ArticleDetailViewModel mViewModel;
    public ActivityArticleDetailBinding binding;
    private PagerFragmentStateAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleDetailViewModel.class);
        binding = ActivityArticleDetailBinding.inflate(getLayoutInflater());
        mAdapter = new PagerFragmentStateAdapter(this,
                getIntent().getStringArrayListExtra(URLS_KEY));
        binding.detailPager.setAdapter(mAdapter);
        setContentView(binding.getRoot());
        binding.detailPager.setCurrentItem(getIntent().getIntExtra(SELECT_INDEX, 0));
    }

    private static String SELECT_INDEX = "SELECT-INDEX";
    private static String URLS_KEY = "URLS";
    public static void transition(Activity activity, int selectIndex, ArrayList<String> urls) {
        Intent intent = new Intent(activity, ArticleDetailActivity.class);
        intent.putExtra(SELECT_INDEX, selectIndex);
        intent.putStringArrayListExtra(URLS_KEY, urls);
        activity.startActivity(intent);
    }
}