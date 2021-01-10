package com.example.classrecord;


import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    private DAO dao;
    private LiveData<List<Table>> liveDataTb;
    Repository(DAO dAO){
        dao = dAO;
        liveDataTb = dao.getAllClass();
    }

    LiveData<List<Table>> getAll(){ return liveDataTb; }

    void delete(Table table){
        new DeleteAsyncTask(dao).execute(table);
    }

    void insert(Table table){
        new InsertAsyncTask(dao).execute(table);
    }

    private static class InsertAsyncTask extends AsyncTask<Table, Void, Void>{
        private DAO dAO;
        private InsertAsyncTask(DAO dAO){
            this.dAO = dAO;
        }
        @Override
        protected Void doInBackground(Table... tables) {
            dAO.insert(tables[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Table, Void, Void>{
        private DAO dAO;
        private DeleteAsyncTask(DAO dAO){
            this.dAO = dAO;
        }
        @Override
        protected Void doInBackground(Table... tables) {
            dAO.delete(tables[0]);
            return null;
        }
    }

}
