package com.example.classrecord;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface LinkDao {
    @Insert
    void insert(LinkTable linkTable);

    @Delete
    void delete(LinkTable linkTable);

    @Query("SELECT * FROM link_table")
    LiveData<List<LinkTable>> getAllLinks();

}
