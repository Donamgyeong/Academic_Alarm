package com.dnk.academicalarm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject")
    LiveData<List<Subject>> getAll();

    @Insert
    void insert(Subject subject);

    @Update
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Query("DELETE FROM Subject")
    void deleteAll();

    @Query("SELECT * FROM subject")
    List<Subject> getSubjects();
}
