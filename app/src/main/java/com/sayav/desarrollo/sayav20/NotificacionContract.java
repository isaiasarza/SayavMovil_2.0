package com.sayav.desarrollo.sayav20;

import android.provider.BaseColumns;

/**
 * Created by Naza on 14/6/2017.
 */

public class NotificacionContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NotificacionContract() {
    }

    /* Inner class that defines the table contents */
    public static class NotificacionEntry implements BaseColumns {
        public static final String _ID = "_id";

        public static final String TABLE_NAME = "notificaciones";
        public static final String COLUMN_NAME_DATE = "fecha";
        public static final String COLUMN_NAME_DESCRIPTION = "descripcion";

        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + NotificacionEntry.TABLE_NAME + " (" +
                        NotificacionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        NotificacionEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " +  NotificacionEntry.TABLE_NAME;
    }

}
