package com.example.rssreader.ui.channel.dialog;

import android.util.Patterns;

import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.ChannelRepository;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;

@HiltViewModel
public class ChannelDialogViewModel extends ViewModel {
    public MutableLiveData<ChannelData> pChannel = new MutableLiveData<>(new ChannelData());
    public MutableLiveData<String> pSnackbar = new MutableLiveData<>("");
    private final ChannelRepository repository;

    @Inject
    ChannelDialogViewModel(ChannelRepository repository) {
        this.repository = repository;
    }

    public void regist(boolean isEdit) {
        if (isEdit) {
            repository.update(pChannel.getValue());
            return;
        }
        repository.insert(pChannel.getValue());
    }

    public boolean isValidUrl() {
        return Patterns.WEB_URL.matcher(pChannel.getValue().link).matches();
    }

    public Flowable<ChannelData> loadChannel() {
        return repository.select(pChannel.getValue().id);
    }
}
