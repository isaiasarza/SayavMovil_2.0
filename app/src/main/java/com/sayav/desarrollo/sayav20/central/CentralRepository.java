package com.sayav.desarrollo.sayav20.central;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sayav.desarrollo.sayav20.room.SayavRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CentralRepository {

    private CentralDao mCentralDao;
    private LiveData<List<Central>> mAllCentrals;

    CentralRepository(Application application) {
        SayavRoomDatabase db = SayavRoomDatabase.getDatabase(application);
        mCentralDao = db.centralDao();
        mAllCentrals = mCentralDao.getAllCentrals();
    }

    LiveData<List<Central>> getAllCentrals() {
        return mAllCentrals;
    }


    public void insert (Central central) {
        new insertAsyncTask(mCentralDao).execute(central);
    }

    public Central getCentral(Central central) throws ExecutionException, InterruptedException {
        return new getAsyncTask(mCentralDao).execute(central).get();
    }

    private static class insertAsyncTask extends AsyncTask<Central, Void, Void> {

        private CentralDao mAsyncTaskDao;

        insertAsyncTask(CentralDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Central... params) throws SQLiteConstraintException {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class getAsyncTask extends AsyncTask<Central,Void, Central> {

        private CentralDao mAsyncTaskDao;

        getAsyncTask(CentralDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Central doInBackground(final Central... params) throws SQLiteConstraintException {
        //    mAsyncTaskDao.insert(params[0]);
            return mAsyncTaskDao.getCentral(params[0].getSubdominio(),params[0].getPuerto());
        }
    }
}
