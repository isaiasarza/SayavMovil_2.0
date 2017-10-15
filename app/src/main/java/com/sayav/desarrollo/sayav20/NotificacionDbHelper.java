package com.sayav.desarrollo.sayav20;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Naza on 14/6/2017.
 */

public class NotificacionDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Notificaciones.db";
    public SQLiteDatabase myDb;
    public NotificacionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotificacionContract.NotificacionEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        this.myDb = db;
        this.myDb.execSQL(NotificacionContract.NotificacionEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /** Returns all the customers in the table */
    public Cursor getAllNotificaciones(){
        this.myDb = this.getReadableDatabase();
        return myDb.query(NotificacionContract.NotificacionEntry.TABLE_NAME, new String[] { NotificacionContract.NotificacionEntry._ID, NotificacionContract.NotificacionEntry.COLUMN_NAME_DESCRIPTION} ,
                null, null, null, null,
                NotificacionContract.NotificacionEntry._ID + " desc ");
    }
}
