package com.example.classrecord;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecViewModel extends AndroidViewModel {

    private Repository repository;
    private LinkRepo linkRepo;

    private LiveData<List<Table>> liveData;
    private LiveData<List<LinkTable>> linkLiveData;

    public RecViewModel(@NonNull Application application) {
        super(application);
        DAO dao = RecDatabase.getDatabase(application).recDao();
        LinkDao linkDao = RecDatabase.getDatabase(application).linkDao();

        repository = new Repository(dao);
        linkRepo = new LinkRepo(linkDao);

        linkLiveData = linkRepo.getAllLinks();
        liveData = repository.getAll();
    }

    LiveData<List<Table>> getAllSub(){ return liveData; }

    LiveData<List<LinkTable>> getAllLink(){ return linkLiveData; }


    void deleteClass(Table table){ repository.delete(table); }
    void insertClass(Table table){ repository.insert(table); }

    void insertLink(LinkTable linkTable){ linkRepo.insert(linkTable); }
    void deleteLink(LinkTable linkTable){ linkRepo.delete(linkTable);}
}
