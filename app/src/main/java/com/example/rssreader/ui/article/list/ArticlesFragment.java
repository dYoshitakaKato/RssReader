package com.example.rssreader.ui.article.list;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentArticlesBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.ui.CategorySelectListener;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.RssParser;
import com.example.rssreader.ui.article.detail.ArticleDetailActivity;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ArticlesFragment extends Fragment implements ItemClickListener<Article>, CategorySelectListener {
    private ArticleViewModel mViewModel;
    private FragmentArticlesBinding binding;
    private int mCategoryId = 0;
    private ArticleAdapter mAdapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false);
        binding.setLifecycleOwner(this);
        mAdapter = new ArticleAdapter(binding);
        binding.arctilesRecyclerView.setAdapter(mAdapter);
        mViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        mViewModel.setOnItemClickListener(this);
        binding.setViewModel(mViewModel);
        setObserver();
        updateCategoryId(mCategoryId);
        return binding.getRoot();
    }

    private void setObserver() {
        mViewModel.articlesListLive.observe(binding.getLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                mAdapter.submitList(articles);
            }
        });
        mViewModel.channelsListLive.observe(binding.getLifecycleOwner(), new Observer<List<Channel>>() {
            @Override
            public void onChanged(@Nullable final List<Channel> newChannel) {
                mViewModel.articlesListLive.postValue(parseArticle(newChannel));
                mViewModel.isLoading.postValue(false);
            }
        });
        mViewModel.pCategoryId.observe(binding.getLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mViewModel.isLoading.postValue(true);
                mViewModel.channelsListLive.postValue(new ArrayList<>());
                mViewModel.fetchCategoryFeed(integer);
            }
        });
    }

    private List<Article> parseArticle(List<Channel> channels) {
        RssParser parser = new RssParser();
        List<Article> list = new ArrayList<>();
        for (Channel channel : channels) {
            for (Article article: channel.getArticles()) {
                StringReader stream = new StringReader(article.getContent());
                try {
                    list.add(parser.parse(stream, article));
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetworkAvailable()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.alert_message)
                .setTitle(R.string.alert_title)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_positive, null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void updateCategoryId(int categoryId) {
        mViewModel.pCategoryId.postValue(categoryId);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String CATEGORY_ID = "category-id";
    public static ArticlesFragment newInstance(int categoryId) {
        Bundle arguments = createBundle(categoryId);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public static Bundle createBundle(int categoryId) {
        Bundle arguments = new Bundle();
        arguments.putInt(CATEGORY_ID, categoryId);
        return arguments;
    }

    @Override
    public void onItemClicked(Article item) {
        ArticleDetailActivity.transition(this.getActivity(), item.getSourceUrl());
    }

    @Override
    public void onSelectedCategory(CategoryData category) {
        updateCategoryId(category.id);
    }
}
