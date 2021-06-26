package com.example.rssreader.repository;

import com.example.rssreader.dao.CategoryDao;
import com.example.rssreader.db.AppDatabase;
import com.example.rssreader.entities.CategoryData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class CategoryRepository {
    CategoryDao dao;
    @Inject
    CategoryRepository(CategoryDao dao) {
        this.dao = dao;
    }

    public Flowable<List<CategoryData>> getAll(){
        return dao.selectAll();
    }

    public Flowable<CategoryData> loadAllById(int id) {
        return dao.loadAllById(id);
    }

    public void insertAll(CategoryData... data){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(data);
            }
        });
    }

    public void delete(CategoryData data){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(data);
            }
        });
    }

    public void update(CategoryData data) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(data);
            }
        });
    }
}
