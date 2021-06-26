package com.example.rssreader.ui.category.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.CategoryItemBinding;
import com.example.rssreader.entities.CategoryData;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

class CategoryViewHolder extends RecyclerView.ViewHolder {

    private final CategoryItemBinding binding;

    public CategoryViewHolder(@NonNull CategoryItemBinding itemBinding)  {
        super(itemBinding.getRoot());
        binding = itemBinding;
        this.getAbsoluteAdapterPosition();
    }

    public void bind(@NonNull CategoryData item, @NonNull LifecycleOwner lifecycleOwner, @NonNull CategoryRecycleViewModel viewModel) {
        binding.setCategory(item);
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    public static CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                        @NonNull int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        CategoryItemBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.category_item, parent, false);
        return new CategoryViewHolder(itemBinding);
    }

    public void onBindViewHolder(LifecycleOwner owner, CategoryRecycleViewModel viewModel , CategoryData item) {
        bind(item, owner, viewModel);
    }
}
