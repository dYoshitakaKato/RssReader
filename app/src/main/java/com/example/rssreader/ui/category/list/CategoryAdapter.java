package com.example.rssreader.ui.category.list;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.CategoryRecycleFragmentBinding;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.generated.callback.OnClickListener;
import com.example.rssreader.ui.article.list.ArticleViewHolder;
import com.prof.rssparser.Article;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CategoryAdapter extends ListAdapter<CategoryData, CategoryViewHolder> {
    private final CategoryRecycleFragmentBinding binding;
    public CategoryAdapter(CategoryRecycleFragmentBinding binding) {
        super(DIFF_CALLBACK);
        this.binding = binding;
    }

    private static final DiffUtil.ItemCallback<CategoryData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CategoryData>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull CategoryData oldData, @NonNull CategoryData newData) {
                    return oldData.id == newData.id;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull CategoryData oldData, @NonNull CategoryData newData) {
                    if (oldData.id != newData.id) {
                        return false;
                    }
                    return Objects.equals(oldData.name, newData.name);
                }
            };

    private int mPosition = -1;
    public int getPosition() { return mPosition; }

    @NonNull
    @NotNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CategoryViewHolder holder = CategoryViewHolder.onCreateViewHolder(parent, viewType);
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0, R.string.add, 0, R.string.add);
                contextMenu.add(0, R.string.edit, 0, R.string.edit);
                contextMenu.add(0, R.string.delete, 0, R.string.delete);
                contextMenu.add(0, R.string.channel_list, 0, R.string.channel_list);
                mPosition = holder.getAbsoluteAdapterPosition();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryViewHolder holder, int position) {
        holder.onBindViewHolder(binding.getLifecycleOwner(), binding.getViewModel(), getItem(position));
    }
}
