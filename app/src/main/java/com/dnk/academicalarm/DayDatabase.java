package com.dnk.academicalarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Day.class}, version = 1)
public abstract class DayDatabase extends RoomDatabase {
    private static DayDatabase INSTANCE;
    public abstract DayDao dayDao();

    public static DayDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, DayDatabase.class, "day-db").build();
        }
        return INSTANCE;
    }

    public void destroyInstance(){
        INSTANCE = null;
    }
}
