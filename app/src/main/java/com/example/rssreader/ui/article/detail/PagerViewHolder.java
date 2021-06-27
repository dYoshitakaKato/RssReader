package com.example.rssreader.ui.article.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.ActivityArticleDetailBinding;
import com.example.rssreader.databinding.PagerFragmentBinding;
import com.prof.rssparser.Article;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class PagerViewHolder extends RecyclerView.ViewHolder {

    private final PagerFragmentBinding mBinding;
    public PagerViewHolder(PagerFragmentBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(@NonNull String item, @NonNull LifecycleOwner lifecycleOwner, @NonNull PagerViewModel viewModel) {
        mBinding.setLifecycleOwner(lifecycleOwner);
        mBinding.setViewModel(viewModel);
        mBinding.executePendingBindings();
    }

    public void onBindViewHolder(LifecycleOwner owner, PagerViewModel viewModel , String item) {
        bind(item, owner, viewModel);
    }

    public static PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        @NonNull int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        PagerFragmentBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.pager_fragment, parent, false);
        return new PagerViewHolder(itemBinding);
    }
}
