package com.example.classrecord;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "link_table")
public class LinkTable {
    @PrimaryKey(autoGenerate = true)
    int id = 0;

    @ColumnInfo(name = "subject_name")
    public String className;

    @ColumnInfo(name = "link_name")
    public String link;

    LinkTable(){}

    public LinkTable(String link, String sub){
        this.link = link;
        this.className = sub;
    }
}
