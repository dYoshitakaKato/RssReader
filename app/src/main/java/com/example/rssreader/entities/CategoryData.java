package com.example.rssreader.entities;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "category", indices = {@Index(value = "category_id", unique = true)})
public class CategoryData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    public int id = 0;

    @Nullable
    @ColumnInfo(name = "name")
    public String name;
}
