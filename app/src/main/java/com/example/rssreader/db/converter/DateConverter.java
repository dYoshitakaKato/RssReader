package com.example.rssreader.db.converter;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {
    @TypeConverter
    public static Date fromDate(Long value) {
        if (value == null) {
            return new Date(System.currentTimeMillis());
        }
        return new Date(value);
    }

    // Date型をLong型に変換
    @TypeConverter
    public static Long dateToLong(Date date) {
        if (date == null)
        {
            return System.currentTimeMillis();
        }
        return date.getTime();
    }
}
