package com.dnk.academicalarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DayDao {
    @Query("SELECT * FROM Day")
    LiveData<List<Subject>> getAll();

    @Insert
    void insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

    @Query("DELETE FROM Day")
    void deleteAll();

    @Query("SELECT * FROM Day WHERE id=ID")
    Day getDay(int ID);

    @Query("SELECT * FROM Day")
    List<Day> getDays();
}
