package com.example.rssreader.ui.category.list;

import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.repository.CategoryRepository;
import com.example.rssreader.ui.CategoryAddClickListener;
import com.example.rssreader.ui.ItemClickListener;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class CategoryRecycleViewModel extends ViewModel {
    private final CategoryRepository repository;
    private ItemClickListener<CategoryData> listener;
    private CategoryAddClickListener addClickListener;
    @Inject
    public CategoryRecycleViewModel(CategoryRepository repository) {
        this.repository = repository;
    }

    public void RegistListener(ItemClickListener<CategoryData> listener, CategoryAddClickListener addClickListener) {
        this.listener = listener;
        this.addClickListener = addClickListener;
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

    public void onChannelAddClick(CategoryData data) {
        addClickListener.onCategoryAddClick(data);
    }
}