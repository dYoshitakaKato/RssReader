package com.example.rssreader.ui.category.dialog;

import android.util.Log;

import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.repository.CategoryRepository;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

@HiltViewModel
public class CategoryDialogFragmentViewModel extends ViewModel {

    public MutableLiveData<CategoryData> pData = new MutableLiveData<>(new CategoryData());
    private final CategoryRepository mRepository;
    public MutableLiveData<String> pSnackbar = new MutableLiveData<>("");

    @Inject
    CategoryDialogFragmentViewModel(CategoryRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void regist() {
        if (pData.getValue().id == 0) {
            mRepository.insertAll(pData.getValue());
        }
        mRepository.update(pData.getValue());
    }

    public void loadCategory(int categoryId) {
        if (categoryId == -1) {
            return;
        }
        mRepository.loadAllById(categoryId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> pData.postValue(value),
                        throwable -> Log.e(TAG, "Unable to get username", throwable));
    }

    public boolean isValid() {
        CategoryData data = pData.getValue();
        if (data == null) {
            return false;
        }
        return data.name != null;
    }
}

