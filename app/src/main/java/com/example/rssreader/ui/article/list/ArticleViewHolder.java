package com.example.rssreader.ui.article.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.ArticleItemBinding;
import com.prof.rssparser.Article;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    private final ArticleItemBinding binding;

    public ArticleViewHolder(ArticleItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Article item, LifecycleOwner lifecycleOwner, ArticleViewModel viewModel) {
        binding.setArticle(item);
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    public static ArticleViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ArticleItemBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.article_item, parent, false);
        return new ArticleViewHolder(itemBinding);
    }

    public void onBindViewHolder(LifecycleOwner owner, ArticleViewModel viewModel ,Article item) {
        bind(item, owner, viewModel);
    }
}
