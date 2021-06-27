package com.example.rssreader.ui.article.list;

import android.view.ViewGroup;

import com.example.rssreader.databinding.FragmentArticlesBinding;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ArticleAdapter extends ListAdapter<com.prof.rssparser.Article, ArticleViewHolder> {
    private FragmentArticlesBinding binding;

    public ArticleAdapter(FragmentArticlesBinding binding) {
        super(DIFF_CALLBACK);
        this.binding = binding;
    }

    public static final DiffUtil.ItemCallback<com.prof.rssparser.Article> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<com.prof.rssparser.Article>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull com.prof.rssparser.Article oldUser, @NonNull com.prof.rssparser.Article newUser) {
                    if (newUser.getSourceUrl() == null) {
                        return false;
                    }
                    if (oldUser.getSourceUrl() == null){
                        return false;
                    }
                    return oldUser.getSourceUrl().equals(newUser.getSourceUrl());
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull com.prof.rssparser.Article oldUser, @NonNull com.prof.rssparser.Article newUser) {
                    return oldUser.equals(newUser);
                }
            };

    @NonNull
    @NotNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return ArticleViewHolder.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ArticleViewHolder holder, int position) {
        holder.onBindViewHolder(binding.getLifecycleOwner(), binding.getViewModel(), getItem(position), position);
    }
}
