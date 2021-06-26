package com.example.rssreader.ui.article.detail;

import com.example.rssreader.databinding.ActivityArticleDetailBinding;

import androidx.lifecycle.ViewModel;

public class ArticleDetailViewModel extends ViewModel {
    public final String url;
    private final ActivityArticleDetailBinding binding;
    public ArticleDetailViewModel(ActivityArticleDetailBinding binding, String url) {
        this.url = url;
        this.binding = binding;
    }
}
