package com.sayav.desarrollo.sayav20;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sayav.desarrollo.sayav20.usuario.Usuario;
import com.sayav.desarrollo.sayav20.usuario.UsuarioExistenteException;
import com.sayav.desarrollo.sayav20.usuario.UsuarioRepository;

import java.util.concurrent.ExecutionException;

import static com.sayav.desarrollo.sayav20.R.layout.activity_registration;


/**
 * Created by luna on 18/06/17.
 */

public class actividadRegistrar extends AppCompatActivity {

    private UsuarioRepository usuarioRepository;
    private Usuario usuario;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_registration);
        usuarioRepository = new UsuarioRepository(getApplication());

        FloatingActionButton fab = findViewById(R.id.fab_registrar);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                EditText nombre, apellido, email, password, password2;

                nombre = (EditText) findViewById(R.id.nombreId);
                apellido = (EditText) findViewById(R.id.apellidoId);
                email = (EditText) findViewById(R.id.emailId);
                password = (EditText) findViewById(R.id.passwordId);
                password2 = (EditText) findViewById(R.id.password2Id);

                if(estanVacion(nombre.getText().toString(),apellido.getText().toString(),email.getText().toString(),password.getText().toString(),password2.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!validarEmail(email.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!verificarContraseña(password.getText().toString(),password2.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
                Usuario usuario = new Usuario(nombre.getText().toString(),apellido.getText().toString(),email.getText().toString(),password.getText().toString());
                try {
                    usuarioRepository.insert(usuario);
                    setRegistrar();
                    Snackbar.make(view,"El usuario fue registrado exitosamente",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_green_light).show();
                    regresarMainPrincipal();

                } catch (UsuarioExistenteException e) {
                    e.printStackTrace();
                    Snackbar.make(view,"El usuario ya existe",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_blue_light).show();
                    regresarMainPrincipal();

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                    Snackbar.make(view,"No se pudo registrar el usuario",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_red_light).show();
                }
            }
        });
       // recyclerView = findViewById(R.id.registration);

    }




    @SuppressLint("ResourceType")
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
        Usuario usuario = new Usuario(nombre.getText().toString(),apellido.getText().toString(),email.getText().toString(),password.getText().toString());
        try {
            usuarioRepository.insert(usuario);
            setRegistrar();
            Snackbar.make(view,"El usuario fue registrado exitosamente",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_green_light).show();
            regresarMainPrincipal();

        } catch (UsuarioExistenteException e) {
            e.printStackTrace();
            Snackbar.make(view,"El usuario ya existe",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_blue_light).show();
            regresarMainPrincipal();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Snackbar.make(view,"No se pudo registrar el usuario",Snackbar.LENGTH_SHORT).setActionTextColor(android.R.color.holo_red_light).show();
        }
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
