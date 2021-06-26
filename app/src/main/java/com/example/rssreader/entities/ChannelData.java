package com.example.rssreader.entities;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "channel",
        indices = {@Index(value = "channel_id", unique = true),
                @Index(value = "category_id", unique = false)})
public class ChannelData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "channel_id")
    public int id = 0;

    @ColumnInfo(name = "link")
    @NonNull
    public String link;

    @ColumnInfo(name = "name")
    @Nullable
    public String name;

    @ColumnInfo(name = "category_id")
    public int categoryId;
}
