package com.example.classrecord;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LinkRepo {

    private LinkDao dao;

    private LiveData<List<LinkTable>> linkTableLiveData;

    LinkRepo(LinkDao dao){
        this.dao = dao;
        linkTableLiveData = dao.getAllLinks();
    }

    LiveData<List<LinkTable>> getAllLinks(){
        return linkTableLiveData;
    }

    void insert(LinkTable table){
        new InsertAsyncTask(dao).execute(table);
    }
    void delete(LinkTable table){ new DeleteAsyncTask(dao).execute(table); }

    private static class InsertAsyncTask extends AsyncTask<LinkTable, Void, Void> {
        private LinkDao dAO;
        private InsertAsyncTask(LinkDao dAO){
            this.dAO = dAO;
        }

        @Override
        protected Void doInBackground(LinkTable... linkTables) {
            dAO.insert(linkTables[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<LinkTable, Void, Void> {
        private LinkDao dAO;
        private DeleteAsyncTask(LinkDao dAO){
            this.dAO = dAO;
        }

        @Override
        protected Void doInBackground(LinkTable... linkTables) {
            dAO.delete(linkTables[0]);
            return null;
        }
    }
}
