package com.sayav.desarrollo.sayav20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                verificarUsuario();
            }
        });
        if(!isRegister()){
            Log.i("LoginActivity","Usuario no registrado");
            iniciarRegistro();
           /* TextView lblGotoRegister = (TextView) findViewById(R.id.link_to_register);
            lblGotoRegister.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                    iniciarRegistro();
                }
            });*/
        }
    }

    private boolean isRegister() {
        Log.i("LoginActivity","Verificando Registro");
        EditText email, password;


        SharedPreferences sharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE);
        Log.i("LoginActivity","Is register: " + Boolean.toString(sharedPreferences.getBoolean(String.valueOf(R.string.isRegister),false)));
        return sharedPreferences.getBoolean(String.valueOf(R.string.isRegister),false);
    }


    public void iniciarRegistro(){

        Intent intent = new Intent(this, actividadRegistrar.class);
        startActivity(intent);

    }

    private boolean validarEmail(String email){
        Log.i("LoginActivity","Validando Email");

        if(email == null || email.isEmpty()){
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void verificarUsuario() {
        Log.i("LoginActivity","Verificando Usuario");

        EditText email, password;

        email = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.passwordId);

        if(!validarEmail(email.getText().toString())){
            Toast.makeText(this, "Email incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }
        BDSayavMovil db = new BDSayavMovil(this);
        Cursor cursor = db.obtenerUsuario(email.getText().toString(), password.getText().toString(), this);
        Log.i("LoginActivity","Cursor count:  " + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Log.i("LoginActivity","Usuario " + cursor.getString(1));
            Log.i("LoginActivity","Password " + cursor.getString(4));

            if(verificarPassword(password.getText().toString(), cursor.getString(4))){
                Toast.makeText(this, "Bienvenido: " + cursor.getString(1) + " " + cursor.getString(2), Toast.LENGTH_SHORT).show();
                setBooleanControl();
                Intent intent = new Intent(this, VincularActivity.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "Password Incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "El usuario no es correcto", Toast.LENGTH_SHORT).show();
        db.close();
    }

    private void setBooleanControl() {

        SharedPreferences sharedPreferences = getSharedPreferences("sesion", MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(String.valueOf(R.string.sesion),true);
        edit.commit();

    }

    public boolean verificarPassword(String passwordInput, String passwordBD){
        if(passwordInput.equals(passwordBD))
            return true;
        return false;
    }
}
