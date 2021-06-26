package com.example.rssreader.ui.channel.list;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentChannelItemBinding;
import com.example.rssreader.databinding.FragmentChannelListBinding;
import com.example.rssreader.entities.ChannelData;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ChannelData}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ChannelRecyclerViewAdapter extends ListAdapter<ChannelData, ChannelHolder> {

    private final FragmentChannelListBinding binding;
    public ChannelRecyclerViewAdapter(FragmentChannelListBinding binding) {
        super(DIFF_CALLBACK);
        this.binding = binding;
    }

    private static final DiffUtil.ItemCallback<ChannelData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChannelData>() {
                @Override
                public boolean areItemsTheSame(@NonNull @NotNull ChannelData oldItem, @NonNull @NotNull ChannelData newItem) {
                    if (oldItem.categoryId != newItem.categoryId) {
                        return false;
                    }
                    return oldItem.link.equals(newItem.link);
                }

                @Override
                public boolean areContentsTheSame(@NonNull @NotNull ChannelData oldItem, @NonNull @NotNull ChannelData newItem) {
                    if (oldItem.categoryId != newItem.categoryId) {
                        return false;
                    }
                    if (oldItem.name != newItem.name) {
                        return false;
                    }
                    return oldItem.link.equals(newItem.link);
                }
            };
    private int mPosition = -1;

    @Override
    public ChannelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentChannelItemBinding binding = FragmentChannelItemBinding.inflate(LayoutInflater.from(parent.getContext()));
        ChannelHolder holder = new ChannelHolder(binding);
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0, R.string.edit, 0, R.string.edit);
                contextMenu.add(0, R.string.delete, 0, R.string.delete);
                mPosition = holder.getAbsoluteAdapterPosition();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChannelHolder holder, int position) {
        holder.onBindViewHolder(binding.getLifecycleOwner(), binding.getViewModel(), getItem(position));
    }

    public int getPosition() {
        return mPosition;
    }
}