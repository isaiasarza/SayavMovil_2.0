package com.sayav.desarrollo.sayav20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralViewModel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by Naza on 13/6/2017.
 */

public class VincularActivity extends MenuActivity {

    private Button button;
    private EditText textView;
    private boolean tokenOnServer;
    private String central;
    private String token;
    private String phoneNumber;
    private MyFirebaseIDService firebase;
    private CentralViewModel centralViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        centralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);

        firebase = new MyFirebaseIDService(this);
        button = (Button) findViewById(R.id.guardarToken);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                vincular();
            }
        });

    }

    private void verBandeja() {
        Intent intent = new Intent(this, BandejaEntrada.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void guardarToken() {

    }

    public String getMyPhoneNO() {
        return "";
    }

    public void vincular() {
        centralViewModel.insert(new Central("isaunp.ddns.net",20000));
        SharedPreferences sharedPreferences = getSharedPreferences("datos", MODE_PRIVATE);

        textView = (EditText) findViewById(R.id.dominioCampo);
        central = textView.getText().toString();

        token = sharedPreferences.getString(String.valueOf(R.string.token), "");
        if (token.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "El token todavía no esta disponible, intente en otro momento", Toast.LENGTH_SHORT).show();
            return;
        }
        Vinculator vinculator = new Vinculator();
        vinculator.execute(central, token, phoneNumber);
        try {
            tokenOnServer = vinculator.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(tokenOnServer) {
            Toast.makeText(getApplicationContext(),
                    "Vinculacion con central"+ central + "exitosa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),
                    "No se pudo vincular con la central"+ central, Toast.LENGTH_SHORT).show();
        }
    }

    private class Vinculator extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            String url = strings[0];
            String token = strings[1];
            String phoneNumber = strings[2];

            return sendRegistrationToServer(url, token, phoneNumber);
        }

        public Boolean sendRegistrationToServer(String server, String token, String phoneNumber) {

            String url = "http://" + server + "/notification/" + token + "/";
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("Token", token)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                client.newCall(request).execute();
                tokenOnServer = true;
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                tokenOnServer = false;

            }
            return false;
        }

    }
}
