package com.royce.sololeveling;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class Converters {
    @TypeConverter
    // returns a Calendar object that can be stored in the DB
    public static Calendar toCalendar(Long value) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(value);
        return  c;
    }

    @TypeConverter
    public static Long fromCalendat(Calendar c) {
        return c == null ? null : c.getTime().getTime();
    }
}
