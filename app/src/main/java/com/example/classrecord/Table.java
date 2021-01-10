package com.example.classrecord;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subject_table")
public class Table {
    @PrimaryKey(autoGenerate = true)
    public int number;

    public String subject;

    public Table(String subject){
        this.subject = subject;
    }

}
