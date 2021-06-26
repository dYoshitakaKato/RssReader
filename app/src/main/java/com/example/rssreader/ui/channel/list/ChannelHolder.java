package com.example.rssreader.ui.channel.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rssreader.R;
import com.example.rssreader.databinding.FragmentChannelItemBinding;
import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.ui.channel.dialog.ChannelDialogViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class ChannelHolder extends RecyclerView.ViewHolder {
    public ChannelData mItem;
    private final FragmentChannelItemBinding binding;

    public ChannelHolder(FragmentChannelItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBindViewHolder(LifecycleOwner lifecycleOwner, ChannelViewModel viewModel, ChannelData item) {
        binding.setChannel(item);
        binding.setLifecycleOwner(lifecycleOwner);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
    }

    public static ChannelHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                   @NonNull int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        FragmentChannelItemBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.fragment_channel_item, parent, false);
        return new ChannelHolder(itemBinding);
    }
}
