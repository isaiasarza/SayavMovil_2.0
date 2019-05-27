package com.sayav.desarrollo.sayav20;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.sayav.desarrollo.sayav20.usuario.Usuario;
import com.sayav.desarrollo.sayav20.usuario.UsuarioRepository;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private UsuarioRepository usuarioRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = (Button) findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    verificarUsuario(v);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        usuarioRepository = new UsuarioRepository(getApplication());
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

    @SuppressLint("ResourceAsColor")
    public void verificarUsuario(View v) throws ExecutionException, InterruptedException {
        Log.i("LoginActivity","Verificando Usuario");
        Usuario usuario = new Usuario();
        EditText email, password;

        email = (EditText) findViewById(R.id.emailId);
        password = (EditText) findViewById(R.id.passwordId);

        if(!validarEmail(email.getText().toString())){
            Toast.makeText(this, "Email incorrecto", Toast.LENGTH_SHORT).show();
            return;
        }
        usuario.setEmail(email.getText().toString());
        usuario.setPassword(password.getText().toString());
        if(usuarioRepository.validarUsuario(usuario)){
            setBooleanControl();
            Intent intent = new Intent(this, CentralesActivity.class);
            startActivity(intent);
        }else{
            Snackbar.make(v,"Credenciales Invalidas",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_red_light).show();
        }
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
