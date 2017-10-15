package com.sayav.desarrollo.sayav20;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Naza on 14/6/2017.
 */

public class BandejaEntrada extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // This is the Adapter being used to display the list's data
    SimpleCursorAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_bandeja);
        // Create a progress bar to display while the list loads
        ProgressBar progressBar = new ProgressBar(this);

        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
        root.addView(progressBar);
        String[] fromColumns = {NotificacionContract.NotificacionEntry._ID,NotificacionContract.NotificacionEntry.COLUMN_NAME_DESCRIPTION};
        int[] toViews = {R.id.notificacion_id,R.id.notificacion_descripcion}; // The TextView in simple_list_item_1
       // ListView mListView = (ListView) findViewById(R.id.list_notificaciones);
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.list_notificacion_item, null,
                fromColumns, toViews, 0);

        setListAdapter(mAdapter);
   // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = NotificacionContentProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
    @Override
    public void onListItemClick (ListView l, View v, int position, long id){

        final int p = position;
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿ Elimina este mensaje ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                NotificacionDbHelper nDbHelper = new NotificacionDbHelper(getBaseContext());
                SQLiteDatabase db = nDbHelper.getWritableDatabase();
                Cursor c = ((SimpleCursorAdapter) getListAdapter()).getCursor();
                c.moveToPosition(p);
                int idItem = c.getInt(c.getColumnIndex(NotificacionContract.NotificacionEntry._ID));
                String[] args = {String.valueOf(idItem)};
                db.delete(NotificacionContract.NotificacionEntry.TABLE_NAME, NotificacionContract.NotificacionEntry._ID + " = ? ", args);
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();
    }

}
