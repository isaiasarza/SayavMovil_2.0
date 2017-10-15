package com.sayav.desarrollo.sayav20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by luna on 18/06/17.
 */

public class BDSayavMovil extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BDSayavMovil.db";
    public static final String TABLA_USUARIO = "usuario";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_NOMBRE = "nombre";
    public static final String COLUMNA_APELLIDO = "apellido";
    public static final String COLUMNA_EMAIL = "email";
    public static final String COLUMNA_PASSWORD = "password";
    private static final String SQL_CREAR = "create table "
            + TABLA_USUARIO + "(" + COLUMNA_ID
            + " integer primary key autoincrement, " + COLUMNA_NOMBRE
            + " text not null, " + COLUMNA_APELLIDO
            + " text not null, " + COLUMNA_EMAIL
            + " text not null, " + COLUMNA_PASSWORD
            + " text not null );";


    public BDSayavMovil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);



    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREAR);

    }





    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


    public int agregar(String nombre, String apellido, String email, String password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMNA_NOMBRE, nombre);
        values.put(COLUMNA_APELLIDO, apellido);
        values.put(COLUMNA_EMAIL, email);
        values.put(COLUMNA_PASSWORD, password);


        //db.insert(TABLA_USUARIO, null,values);

        long newRowId;
        newRowId = db.insert(TABLA_USUARIO, null, values);

        db.close();
        return (int) newRowId;
    }


    public void obtener(int id, Context context){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_APELLIDO, COLUMNA_EMAIL, COLUMNA_PASSWORD};

        Cursor cursor =
                db.query(TABLA_USUARIO,
                        projection,
                        " _id = ?",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);


        if (cursor != null)
            cursor.moveToFirst();

        System.out.println("El nombre es " +  cursor.getString(1) );

        Toast.makeText(context, cursor.getString(1), Toast.LENGTH_SHORT).show();

        db.close();

    }



    public Cursor obtenerUsuario(String email, String password, Context context){

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMNA_ID, COLUMNA_NOMBRE, COLUMNA_APELLIDO, COLUMNA_EMAIL, COLUMNA_PASSWORD};

        String[] argument = {email};
        Cursor cursor =
                db.query(TABLA_USUARIO,
                        projection,
                        " email = ?",
                        new String[] { email },
                        null,
                        null,
                        null,
                        null);


        //db.rawQuery("SELECT "+projection+" FROM "+TABLA_USUARIO+" WHERE "+COLUMNA_EMAIL+" =? ", argument);
        //db.rawQuery("SELECT "+projection+" FROM "+TABLA_USUARIO+" WHERE "+COLUMNA_EMAIL +"= "+email+ " AND "+ COLUMNA_PASSWORD+ "= "+password,null);

        return cursor;
    }











    public void actualizar (String nombre, String apellido, String email, String password, int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre",nombre);
        values.put("apellido",apellido);
        values.put("email",email);
        values.put("password",password);


        int i = db.update(TABLA_USUARIO,
                values,
                " _id = ?",
                new String[] { String.valueOf( id ) });
        db.close();
    }



    public boolean eliminar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_USUARIO,
                    " _id = ?",
                    new String[] { String.valueOf (id ) });
            db.close();
            return true;

        }catch(Exception ex){
            return false;
        }
    }


    public void eliminarBaseDeDatos(Context context){

        context.deleteDatabase(DATABASE_NAME);
    }

}
