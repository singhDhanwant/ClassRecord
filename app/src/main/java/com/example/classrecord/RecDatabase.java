package com.example.classrecord;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Table.class, LinkTable.class}, version = 2, exportSchema = false)
public abstract class RecDatabase extends RoomDatabase {

    public abstract DAO recDao();
    public abstract LinkDao linkDao();

    private static volatile RecDatabase INSTANCE;

    static RecDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RecDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), RecDatabase.class
                    , "record_database").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
