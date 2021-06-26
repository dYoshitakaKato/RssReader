package com.example.rssreader.ui.category.dialog;

import android.util.Log;

import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.repository.CategoryRepository;

import javax.inject.Inject;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.scopes.ActivityScoped;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

@HiltViewModel
public class CategoryDialogFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<CategoryData> data = new MutableLiveData<>(new CategoryData());
    private final CategoryRepository repository;


    @Inject
    CategoryDialogFragmentViewModel(CategoryRepository repository) {
        this.repository = repository;
    }

    public void regist() {
        if (data.getValue().id == 0) {
            repository.insertAll(data.getValue());
        }
        repository.update(data.getValue());
    }

    public void loadCategory(int categoryId) {
        if (categoryId == -1) {
            return;
        }
        repository.loadAllById(categoryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> data.postValue(value),
                        throwable -> Log.e(TAG, "Unable to get username", throwable));
    }
}

