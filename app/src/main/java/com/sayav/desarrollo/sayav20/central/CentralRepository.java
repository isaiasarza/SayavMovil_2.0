package com.sayav.desarrollo.sayav20.central;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sayav.desarrollo.sayav20.room.SayavRoomDatabase;

import java.util.List;

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

    private static class insertAsyncTask extends AsyncTask<Central, Void, Void> {

        private CentralDao mAsyncTaskDao;

        insertAsyncTask(CentralDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Central... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
