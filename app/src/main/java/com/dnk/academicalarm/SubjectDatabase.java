package com.dnk.academicalarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Subject.class}, version = 1)
public abstract class SubjectDatabase extends RoomDatabase {
    private static SubjectDatabase INSTANCE;
    public abstract SubjectDao subjectDao();

    public static SubjectDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, SubjectDatabase.class, "subject-db").build();
        }
        return INSTANCE;
    }

    public void destroyInstance(){
        INSTANCE = null;
    }
}
