package com.sayav.desarrollo.sayav20.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralDao;
import com.sayav.desarrollo.sayav20.usuario.Usuario;
import com.sayav.desarrollo.sayav20.usuario.UsuarioDao;

@Database(entities = {Central.class, Usuario.class},version=4)
public abstract class SayavRoomDatabase extends RoomDatabase {
    public abstract CentralDao centralDao();
    public abstract UsuarioDao usuarioDao();
    private static volatile SayavRoomDatabase INSTANCE;

    public static SayavRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SayavRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SayavRoomDatabase.class, "sayav_room_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

                    private final CentralDao mDao;
                    private final UsuarioDao mUsuarioDao;

                    PopulateDbAsync(SayavRoomDatabase db) {
                        mDao = db.centralDao();
                        mUsuarioDao = db.usuarioDao();
                    }

                    @Override
                    protected Void doInBackground(final Void... params) {
                       //mDao.deleteAll();
                 /*       Central central = new Central("isaunp.ddns.net", 20000);
                        mDao.insert(central);
                        central = new Central("lucasboba.ddns.net", 20001);
                        mDao.insert(central);
                        Log.i("Insert", mDao.getAllCentrals().toString());*/
                        return null;
                    }
                }

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}
