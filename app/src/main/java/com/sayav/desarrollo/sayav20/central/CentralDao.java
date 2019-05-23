package com.sayav.desarrollo.sayav20.central;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CentralDao {
    @Insert
    void insert(Central central) throws SQLiteConstraintException;

    @Query("DELETE FROM central")
    void deleteAll();

    @Query("SELECT * FROM central WHERE subdominio = :subdominio AND puerto = :puerto")
    Central getCentral(String subdominio, int puerto);

    @Query("SELECT * from central ORDER BY subdominio ASC")
    LiveData<List<Central>> getAllCentrals();
}
