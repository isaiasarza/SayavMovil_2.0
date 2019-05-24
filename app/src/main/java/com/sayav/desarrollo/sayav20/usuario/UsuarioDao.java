package com.sayav.desarrollo.sayav20.usuario;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    void insert(Usuario usuario);

    @Query("SELECT * FROM USUARIO")
    LiveData<List<Usuario>> getUsuarios();

    @Query("SELECT * FROM USUARIO WHERE email = :email")
    Usuario getUsuario(String email);
    @Query("SELECT * FROM USUARIO LIMIT 1")
    Usuario getUsuario();

    @Query("SELECT 1 FROM USUARIO WHERE email = :email and password = :password")
    int isUser(String email, String password);
}
