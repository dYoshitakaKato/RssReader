package com.example.rssreader.ui.channel.dialog;

import com.example.rssreader.dao.ChannelDao;
import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.ChannelRepository;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class ChannelDialogViewModel extends ViewModel {
    public MutableLiveData<ChannelData> channel = new MutableLiveData<>(new ChannelData());
    private final ChannelRepository repository;

    @Inject
    ChannelDialogViewModel(ChannelRepository repository) {
        this.repository = repository;
    }

    public void regist(boolean isEdit) {
        if (isEdit) {
            repository.update(channel.getValue());
            return;
        }
        repository.insert(channel.getValue());
    }

    public Flowable<ChannelData> loadChannel() {
        return repository.select(channel.getValue().id);
    }
}
