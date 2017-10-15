package com.sayav.desarrollo.sayav20;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Naza on 14/6/2017.
 */

public class NotificacionContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher ;
    public static final String AUTHORITY  = "com.sayav.desarrollo.sayav20.notificaciones";

    /** A uri to do operations on cust_master table. A content provider is identified by its uri */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY  + "/" + NotificacionContract.NotificacionEntry.TABLE_NAME );

    public NotificacionContentProvider() {

    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY , NotificacionContract.NotificacionEntry.TABLE_NAME, 1);

      //  sUriMatcher.addURI(AUTHORITY , NotificacionContract.NotificacionEntry.TABLE_NAME + "/#", 2);
    }

    NotificacionDbHelper nDbHelper;
    public NotificacionContentProvider(Context ctx) {
        this.nDbHelper = new NotificacionDbHelper(ctx);
    }


    @Override
    public boolean onCreate() {
        this.nDbHelper = new NotificacionDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] PROJECTION, @Nullable String selection, @Nullable String[] strings1, @Nullable String sortOrder) {
        Cursor c = null;

        switch (sUriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 1:
                c = nDbHelper.getAllNotificaciones();

                boolean hay = c.moveToFirst();
                Log.d("TAG", "Hay datos " + hay);
                break;

            // If the incoming URI was for a single row
         //   case 2:

        //        selection = selection + "_ID = " + uri.getLastPathSegment();
         //       break;

            default:

        }
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
