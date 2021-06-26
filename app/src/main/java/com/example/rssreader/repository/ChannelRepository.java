package com.example.rssreader.repository;

import com.example.rssreader.dao.ChannelDao;
import com.example.rssreader.db.AppDatabase;
import com.example.rssreader.entities.CategoryData;
import com.example.rssreader.entities.ChannelData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class ChannelRepository {
    ChannelDao dao;

    @Inject
    ChannelRepository(ChannelDao dao) {
        this.dao = dao;
    }

    public Flowable<List<ChannelData>> selectAll(){
        return dao.selectAll();
    }

    public Flowable<List<ChannelData>> loadAllByCategoryId(int categoryId) {
        return dao.selectAllByCategory(categoryId);
    }

    public Flowable<ChannelData> select(int id){
        return dao.select(id);
    }

    public void insert(ChannelData... data){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(data);
        });
    }

    public void delete(ChannelData data){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(data);
        });
    }

    public void update(ChannelData value) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.update(value);
        });
    }
}
