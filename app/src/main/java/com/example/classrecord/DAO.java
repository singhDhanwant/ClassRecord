package com.example.classrecord;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAO {
    @Insert
    void insert(Table table);

    @Delete
    void delete(Table table);


    @Query("SELECT * FROM subject_table")
    LiveData<List<Table>> getAllClass();

}
