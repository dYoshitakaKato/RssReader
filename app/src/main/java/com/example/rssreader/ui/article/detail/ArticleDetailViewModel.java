package com.example.rssreader.ui.article.detail;

import com.example.rssreader.databinding.ActivityArticleDetailBinding;
import com.prof.rssparser.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArticleDetailViewModel extends ViewModel {
    public MutableLiveData<List<String>> articles = new MutableLiveData<>(new ArrayList<>());
    public ActivityArticleDetailBinding binding;

}
