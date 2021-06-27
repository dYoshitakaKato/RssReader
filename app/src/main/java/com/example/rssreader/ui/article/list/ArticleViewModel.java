package com.example.rssreader.ui.article.list;

import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.ChannelRepository;
import com.example.rssreader.ui.ItemClickListener;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

@HiltViewModel
public class ArticleViewModel extends ViewModel {

    public MutableLiveData<Integer> pCategoryId = new MutableLiveData<>(-1);
    private ItemClickListener<Article> listener;
    private final ChannelRepository repository;

    @Inject
    public ArticleViewModel(ChannelRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Channel>> channelsListLive = new MutableLiveData<>(new ArrayList<>());
    public MutableLiveData<List<Article>> articlesListLive = new MutableLiveData<>(new ArrayList<>());
    private void setChannel(Channel channel) {
        List<Channel> channels = this.channelsListLive.getValue();
        channels.add(channel);
        this.channelsListLive.postValue(channels);
    }

    public void onRefresh() {
        isLoading.postValue(true);
    }

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void setOnItemClickListener(ItemClickListener<Integer> listener) {
        this.listener = listener;
    }

    public void onItemClick(int index) {
        if (listener == null) {
            return;
    }
        listener.onItemClicked(index);
    }

    public void fetchCategoryFeed(int categoryId) {
        if (categoryId == 0) {
            fetchCategoryFeedAll();
            return;
        }
        repository.loadAllByCategoryId(categoryId).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    for (ChannelData channel : data) {
                        fetchFeed(channel.link);
                    }
                });
    }

    public void fetchFeed(String urlString) {

        Parser parser = new Parser.Builder()
                .build();

        parser.onFinish(new OnTaskCompleted() {

            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(@NonNull Channel channel) {
                setChannel(channel);
            }

            //what to do in case of error
            @Override
            public void onError(@NonNull Exception e) {
                setChannel(new Channel(null, null, null, null, null, null, new ArrayList<com.prof.rssparser.Article>()));
                e.printStackTrace();
                snackbar.postValue("An error has occurred. Please try again");
            }
        });
        parser.execute(urlString);
    }
}
