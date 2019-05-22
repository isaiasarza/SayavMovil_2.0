package com.sayav.desarrollo.sayav20.central;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CentralViewModel extends AndroidViewModel {
    private CentralRepository centralRepository;
    private LiveData<List<Central>> centrales;
    public CentralViewModel(@NonNull Application application) {
        super(application);
        centralRepository = new CentralRepository(application);
        centrales = centralRepository.getAllCentrals();
    }

    public LiveData<List<Central>> getAllCentrals() {
        return centrales;
    }

    public Central getCentral(Central central) throws ExecutionException, InterruptedException {return centralRepository.getCentral(central);};

    public void insert(Central central){
        centralRepository.insert(central);
    }
}
