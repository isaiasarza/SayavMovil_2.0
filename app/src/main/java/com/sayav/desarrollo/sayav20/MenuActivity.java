package com.sayav.desarrollo.sayav20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Naza on 21/6/2017.
 */

public abstract class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE);

        boolean sesion = sharedPreferences.getBoolean(String.valueOf(R.string.sesion), false);


            switch (item.getItemId()) {
                case R.id.BandejaEItem:
                    Intent intent = new Intent(this, BandejaEntrada.class);
                    startActivity(intent);
                    return true;
                case R.id.salirItem:
                    Toast.makeText(this, "Adios", Toast.LENGTH_LONG).show();
                    setFalseSesion();
                    super.finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
    }

    private void setFalseSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(String.valueOf(R.string.sesion),false);
        edit.commit();
    }


    public void iniciarRegistro(){

        Intent intent = new Intent(this, actividadRegistrar.class);
        startActivity(intent);

    }
}
