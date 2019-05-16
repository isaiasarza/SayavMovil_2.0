package com.sayav.desarrollo.sayav20.central;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CentralDao {
    @Insert
    void insert(Central central);

    @Query("DELETE FROM central")
    void deleteAll();

    @Query("SELECT * from central ORDER BY subdominio ASC")
    LiveData<List<Central>> getAllCentrals();
}
