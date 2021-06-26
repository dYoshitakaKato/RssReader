package com.example.rssreader.ui.category.list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.ActivityMainBinding;
import com.example.rssreader.databinding.CategoryRecycleFragmentBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.ui.CategorySelectListener;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.article.list.ArticlesFragment;
import com.example.rssreader.ui.category.dialog.CategoryDialogFragment;
import com.example.rssreader.ui.channel.dialog.ChannelDialogFragment;
import com.example.rssreader.ui.channel.list.ChannelsActivity;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;

@AndroidEntryPoint
public class CategoryRecycleFragment extends Fragment implements ItemClickListener<CategoryData>{

    private CategoryRecycleViewModel mViewModel;
    private CategoryAdapter adapter;

    public static CategoryRecycleFragment newInstance() {
        return new CategoryRecycleFragment();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CategoryRecycleFragmentBinding binding = CategoryRecycleFragmentBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        mViewModel = new ViewModelProvider(this).get(CategoryRecycleViewModel.class);
        mViewModel.RegistListener(this);
        binding.setViewModel(mViewModel);
        adapter = new CategoryAdapter(binding);
        binding.categoryRecycle.setAdapter(adapter);
        Disposable disposable = mViewModel.reload().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(value -> adapter.submitList(value),
                        throwable -> Snackbar.make(getView(),
                                "Unable to submit category data",  Snackbar.LENGTH_SHORT).show());
        compositeDisposable.add(disposable);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        CategoryData data = adapter.getCurrentList().get(position);
        if (data == null) {
            Snackbar.make(getView(), "Not selected item", Snackbar.LENGTH_SHORT).show();
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.string.edit:
                CategoryDialogFragment.newInstance(data.id).show(getParentFragmentManager(), CategoryDialogFragment.class.getName());
                break;
            case R.string.delete:
                mViewModel.delete(data);
                break;
            case R.string.add:
                ChannelDialogFragment.newInstance(data.id).show(getParentFragmentManager(), ChannelDialogFragment.class.getName());
                break;
            case R.string.channel_list:
                ChannelsActivity.transition(getActivity(), data.id);
                break;
            default:
                Snackbar.make(getView(), "Unexpected value: " + item.getItemId(),  Snackbar.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClicked(CategoryData item) {
        for (Fragment fragment : getParentFragmentManager().getFragments()) {
            if (!(fragment instanceof CategorySelectListener)) {
                continue;
            }
            CategorySelectListener listener = (CategorySelectListener) fragment;
            listener.onSelectedCategory(item);
        }
    }
}