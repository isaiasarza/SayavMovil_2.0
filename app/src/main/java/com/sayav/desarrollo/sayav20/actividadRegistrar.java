package com.sayav.desarrollo.sayav20;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by luna on 18/06/17.
 */

public class actividadRegistrar extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }




    public void saveRegistration (View view) {

        EditText nombre, apellido, email, password, password2;

        nombre = (EditText) findViewById(R.id.nombreId);
        apellido = (EditText) findViewById(R.id.apellidoId);
        email = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.passwordId);
        password2 = (EditText) findViewById(R.id.password2Id);

        if(estanVacion(nombre.getText().toString(),apellido.getText().toString(),email.getText().toString(),password.getText().toString(),password2.getText().toString())){
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!validarEmail(email.getText().toString())){
            Toast.makeText(this, "Email incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!verificarContraseña(password.getText().toString(),password2.getText().toString())){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        BDSayavMovil db = new BDSayavMovil(this);
        int id = db.agregar(nombre.getText().toString(), apellido.getText().toString(), email.getText().toString(), password.getText().toString());
        Toast.makeText(getApplicationContext(), "Registro exitoso!!", Toast.LENGTH_SHORT).show();
        db.obtener(id, this);

        setRegistrar();

        regresarMainPrincipal();


    }

    private boolean estanVacion(String s, String s1, String s2, String s3, String s4) {
        if(s == null || s.isEmpty()){
            return true;
        }
        if(s1 == null || s1.isEmpty()){
            return true;
        }
        if(s2 == null || s2.isEmpty()){
            return true;
        }
        if(s3 == null || s3.isEmpty()){
            return true;
        }
        if(s4 == null || s4.isEmpty()){
            return true;
        }
        return false;
    }

    private void setRegistrar() {
        SharedPreferences sharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(String.valueOf(R.string.isRegister),true);
        edit.commit();
    }

    private boolean verificarContraseña(String s, String s1) {
        if(s == null || s.isEmpty() || s1 == null || s1.isEmpty()){
            return false;
        }
        return s.equals(s1);
    }

    private boolean validarEmail(String email){
        if(email == null || email.isEmpty()){
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void regresarMainPrincipal(){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

}
