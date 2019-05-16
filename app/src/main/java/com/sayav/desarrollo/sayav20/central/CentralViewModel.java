package com.sayav.desarrollo.sayav20.central;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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

    public void insert(Central central){
        centralRepository.insert(central);
    }
}
