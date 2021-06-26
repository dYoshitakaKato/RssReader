package com.example.rssreader.ui.channel.list;

import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.ChannelRepository;
import com.example.rssreader.ui.ItemClickListener;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class ChannelViewModel extends ViewModel {
    public MutableLiveData<List<ChannelData>> channel = new
            MutableLiveData<>();

    @Inject
    ChannelViewModel(ChannelRepository repository) {
        this.repository = repository;
    }
    private final ChannelRepository repository;

    private ItemClickListener<ChannelData> listenr;
    public void setOnClickListenr(ItemClickListener<ChannelData> listenr) {
        this.listenr = listenr;
    }

    public void delete(ChannelData data) {
        repository.delete(data);
    }

    public Flowable<List<ChannelData>> reload(int categoryId) {
        return repository.loadAllByCategoryId(categoryId);
    }

    public void onClick(ChannelData channel) {
        if (listenr == null) {
            return;
        }
        listenr.onItemClicked(channel);
    }
}