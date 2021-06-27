package com.example.rssreader.ui.article.list;

import android.os.Build;

import com.example.rssreader.entities.ChannelData;
import com.example.rssreader.repository.ChannelRepository;
import com.example.rssreader.ui.ItemClickListener;
import com.example.rssreader.ui.RssParser;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ArticleViewModel extends ViewModel {

    public MutableLiveData<Integer> pCategoryId = new MutableLiveData<>(-1);
    private ItemClickListener<Integer> listener;
    private final ChannelRepository repository;
    public MutableLiveData<Boolean> pIsEmptyList = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> pIsInitialLoading = new MutableLiveData<>(false);
    public MutableLiveData<String> pScackbar = new MutableLiveData<>("");

    @Inject
    public ArticleViewModel(ChannelRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Article>> articlesListLive = new MutableLiveData<>(new ArrayList<>());

    private void setChannel(Channel channel) {
        articlesListLive.postValue(parseArticle(channel));
    }

    private List<Article> parseArticle(Channel channel) {
        RssParser parser = new RssParser();
        List<Article> list = new ArrayList<>(articlesListLive.getValue());
        for (Article article : channel.getArticles()) {
            StringReader stream = new StringReader(article.getContent());
            try {
                Article parse = parser.parse(stream, article);
                if (canAdd(parse, list)) {
                    list.add(parse);
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
                pScackbar.postValue("エラーが発生しました。再度実行してください。");
            }
        }
        return list;
    }

    private boolean canAdd(Article article, List<Article> list) {
        for (Article data : list) {
            if (data.getLink().equals(article.getLink())){
                return false;
            }
        }
        return true;
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
            fetchCategoryFeedInternal(repository.selectAll());
            return;
        }
        fetchCategoryFeedInternal(repository.loadAllByCategoryId(categoryId));
    }

    private void fetchCategoryFeedInternal(Flowable<List<ChannelData>> floable) {
        floable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(data -> {
                    if (data.isEmpty()) {
                        articlesListLive.postValue(parseArticle(new Channel()));
                        return;
                    }
                    for (ChannelData channel : data) {
                        fetchFeed(channel.link);
                    }
                });
    }

    public void fetchFeed(String urlString) {

        Parser parser = new Parser.Builder()
                .build();

        parser.onFinish(new OnTaskCompleted() {

            @Override
            public void onTaskCompleted(@NonNull Channel channel) {
                setChannel(channel);
            }

            @Override
            public void onError(@NonNull Exception e) {
                setChannel(new Channel(null, null, null, null, null, null, new ArrayList<com.prof.rssparser.Article>()));
                e.printStackTrace();
                pScackbar.postValue("エラーが発生しました。再度実行してください。");
            }
        });
        parser.execute(urlString);
    }
}
