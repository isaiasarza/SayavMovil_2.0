package com.sayav.desarrollo.sayav20.usuario;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sayav.desarrollo.sayav20.room.SayavRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UsuarioRepository {

    private UsuarioDao mUsuarioDao;
    private LiveData<List<Usuario>> mAllUsuarios;

    public UsuarioRepository(Application application){
        SayavRoomDatabase db = SayavRoomDatabase.getDatabase(application);
        mUsuarioDao = db.usuarioDao();
        mAllUsuarios = mUsuarioDao.getUsuarios();

    }

    LiveData<List<Usuario>> getAllUsuarios() {
        return mAllUsuarios;
    }


    public void insert (Usuario usuario) throws UsuarioExistenteException, ExecutionException, InterruptedException {
        if(getUsuario()!=null)
            throw new UsuarioExistenteException();
        new insertAsyncTask(mUsuarioDao).execute(usuario);
    }

    public Usuario getUsuario() throws ExecutionException, InterruptedException {
        return new getAsyncTask(mUsuarioDao).execute().get();
    }

    public boolean validarUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
        return new validateAsyncTask(mUsuarioDao).execute(usuario).get();
    }

    private static class insertAsyncTask extends AsyncTask<Usuario, Void, Void> {

        private UsuarioDao mAsyncTaskDao;

        insertAsyncTask(UsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Usuario... params) throws SQLiteConstraintException {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class getAsyncTask extends AsyncTask<Void,Void, Usuario> {

        private UsuarioDao mAsyncTaskDao;

        getAsyncTask(UsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Usuario doInBackground(final Void... params) throws SQLiteConstraintException {
            return mAsyncTaskDao.getUsuario();
        }
    }

    private static class validateAsyncTask extends AsyncTask<Usuario,Void, Boolean> {

        private UsuarioDao mAsyncTaskDao;

        validateAsyncTask(UsuarioDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Usuario... params) throws SQLiteConstraintException {
            return mAsyncTaskDao.isUser(params[0].getEmail(),params[0].getPassword()) == 1;
        }
    }

}
