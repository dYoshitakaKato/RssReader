package com.example.rssreader.ui.category.list;

import com.example.rssreader.databinding.ActivityMainBinding;
import com.example.rssreader.databinding.CategoryRecycleFragmentBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.CategoryRepository;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.RssParser;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class CategoryRecycleViewModel extends ViewModel {
    private final CategoryRepository repository;
    private ItemClickListener<CategoryData> listener;
    @Inject
    public CategoryRecycleViewModel(CategoryRepository repository) {
        this.repository = repository;
    }

    public void RegistListener(ItemClickListener<CategoryData> listener) {
        this.listener = listener;
    }

    public Flowable<List<CategoryData>> reload() {
        return repository.getAll();
    }

    public void delete(CategoryData data) {
        repository.delete(data);
    }

    public void onClick(CategoryData data){
        listener.onItemClicked(data);
    }
}