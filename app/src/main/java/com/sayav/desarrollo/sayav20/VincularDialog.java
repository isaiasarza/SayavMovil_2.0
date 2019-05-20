package com.sayav.desarrollo.sayav20;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralViewModel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public class VincularDialog extends DialogFragment {
    private String subdominio;
    private String puerto;
    private View mView;
    Central central;
    private CentralViewModel centralViewModel;
    private MyFirebaseIDService firebase;
    private String token = "";
    boolean tokenOnServer = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        firebase = new MyFirebaseIDService(getContext());

        mView = inflater.inflate(R.layout.dialog_vincular, null);
        centralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);

        final EditText eSubdominio = (EditText)mView.findViewById(R.id.nuevo_subdominio);
        final EditText ePuerto = (EditText) mView.findViewById(R.id.nuevo_puerto);
                // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.vincular_boton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        subdominio = eSubdominio.getText().toString();
                        puerto =  ePuerto.getText().toString();
                        central = new Central(subdominio,Integer.valueOf(puerto).intValue());
                        Log.i("Vinculando", central.toString());
                        vincular();
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VincularDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void vincular() {
        Log.i("Vincular","Vinculando " + central);
        //   centralViewModel.insert(new Central("isaunp.ddns.net",20000));
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("datos", MODE_PRIVATE);

      // final String token = sharedPreferences.getString(String.valueOf(R.string.token), "");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Token", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        // Log and toast
                        Log.i("Token", "Se obtuvo el token");
                     //   Toast.makeText(getContext(), "Se obtuvo el token", Toast.LENGTH_SHORT).show();
                        String nombre = "";
                        //String nombre = sharedPreferences.getString(String.valueOf(R.string.))
                        if (token.isEmpty()) {
                            Snackbar.make(mView,"El token no esta disponible, intente en otro momento",Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        VincularDialog.Vinculator vinculator = new VincularDialog.Vinculator();
                        vinculator.execute(central.getSubdominio()+":"+central.getPuerto(), token, nombre);
                        try {
                            tokenOnServer = vinculator.get();
                        } catch (InterruptedException e) {
                            Log.e("TokenOnServerException",e.getMessage());
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            Log.e("TokenOnServerException",e.getMessage());
                            e.printStackTrace();
                        }
                        Log.e("TokenOnServer",Boolean.toString(tokenOnServer));

                        if(tokenOnServer) {
                            centralViewModel.insert(central);
                            Snackbar.make(mView,"Vinculacion con central "+ central + " exitosa",Snackbar.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(mView,"No se pudo vincular con la central "+ central,Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

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
            Log.i("Send Token","Enviando token a servidor: " + url );

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(60, TimeUnit.SECONDS);
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.writeTimeout(60, TimeUnit.SECONDS);
            OkHttpClient client = new OkHttpClient();

            client = builder.build();

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
