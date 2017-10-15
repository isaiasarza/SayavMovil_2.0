package com.sayav.desarrollo.sayav20;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Naza on 31/10/2016.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIDService";

    private String phoneNumber;
    private String url ;
    private boolean tokenOnServer;
    private int READ_BLOCK_SIZE = 100;
    private static SharedPreferences sharedPreferences;
    private Context ctx;
    public MyFirebaseIDService() {

        this.url = "";
    }

    public MyFirebaseIDService(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.

            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);

            // TODO: 13/6/2017 Persistir token en archivo XML.
            sharedPreferences = getSharedPreferences("datos",MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(String.valueOf(R.string.token),refreshedToken);
            edit.commit();


    }


    // [END refresh_token]

    public boolean isTokenOnServer() {
        return tokenOnServer;
    }

    public void guardarToken(Context ctx, String token){
        sharedPreferences = ctx.getSharedPreferences("datos",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(String.valueOf(R.string.token),token);
        edit.commit();
    }

    public String leerToken(Context ctx){
        sharedPreferences = ctx.getSharedPreferences("datos",MODE_PRIVATE);
        return sharedPreferences.getString(String.valueOf(R.string.token),"");
    }

    public boolean leerTokenOnServer(Context ctx) {
        return false;
    }

    public String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }


    public void guardarTokenOnServer(Context ctx,boolean value) {
        sharedPreferences = ctx.getSharedPreferences("datos",MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(String.valueOf(R.string.tokenOnServer),value);
        edit.commit();
    }
}
