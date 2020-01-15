package com.royce.sololeveling;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@android.arch.persistence.room.Database(entities = {Task_DB.class, Mission_DB.class, RepeatDate_DB.class},  version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {
    public abstract DataAccessObj dataAccessObj();
}
