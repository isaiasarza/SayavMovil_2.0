package com.sayav.desarrollo.sayav20.central;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "central")
public class Central {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String subdominio;
    @NonNull
    private int puerto;

    public Central(String subdominio,int puerto){
        this.subdominio = subdominio;
        this.puerto = puerto;
    }

    public int getPuerto() {
        return puerto;
    }

    public String getSubdominio() {
        return subdominio;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setSubdominio(String subdominio) {
        this.subdominio = subdominio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.subdominio + ":" + this.puerto;
    }
}
