package com.example.rssreader.ui.article.detail;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PagerViewModel extends ViewModel {
    public MutableLiveData<String> pUrl = new MutableLiveData<>("");
    public MutableLiveData<Boolean> pIsLoading = new MutableLiveData<>(false);
}