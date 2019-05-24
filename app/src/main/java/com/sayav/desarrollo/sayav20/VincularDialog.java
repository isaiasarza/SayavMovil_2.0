package com.sayav.desarrollo.sayav20;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
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
import com.sayav.desarrollo.sayav20.usuario.Usuario;
import com.sayav.desarrollo.sayav20.usuario.UsuarioRepository;
import com.sayav.desarrollo.sayav20.vinculacion.CentralAPI;
import com.sayav.desarrollo.sayav20.vinculacion.CentralData;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VincularDialog extends DialogFragment {
    private UsuarioRepository usuarioRepository;
    private String subdominio;
    private String puerto;
    private View mView;
    Central central;
    OnComplete mOnCompleteListener;
    private CentralViewModel centralViewModel;
    private String token = "";
    boolean tokenOnServer = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        mView = inflater.inflate(R.layout.dialog_vincular, null);
        centralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);
        usuarioRepository = new UsuarioRepository(getActivity().getApplication());
        final EditText eSubdominio = (EditText) mView.findViewById(R.id.nuevo_subdominio);
        final EditText ePuerto = (EditText) mView.findViewById(R.id.nuevo_puerto);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mView)
                // Add action buttons
                .setPositiveButton(R.string.vincular_boton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        subdominio = eSubdominio.getText().toString();
                        puerto = ePuerto.getText().toString();
                        if(subdominio == null || subdominio.isEmpty() ){
                            eSubdominio.setError("Debe ingresar el subdominio");
                            return;
                        }
                        if(puerto == null || Integer.valueOf(puerto).intValue() == 0){
                            ePuerto.setError("Debe ingresar el subdominio");
                            return;
                        }
                        central = new Central(subdominio, Integer.valueOf(puerto).intValue());
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

    public interface OnComplete {
        void onComplete(final Bundle bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {

            mOnCompleteListener = (OnComplete) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewItemDialogFragment");
        }
    }

    private void vincular() {
        Log.i("Vincular", "Vinculando " + central);
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
                    String nombre = "";
                    if (token.isEmpty()) {
                        Snackbar.make(mView, "El token no esta disponible, intente en otro momento", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    final Bundle bundle = new Bundle();
                    bundle.putBoolean("vinculado", tokenOnServer);
                    bundle.putString("central", central.getSubdominio());
                    bundle.putInt("puerto", central.getId());
                    String url = "http://" + central.getSubdominio() + ":" + central.getPuerto();
                    Log.i("Send Token", "Enviando token a servidor: " + url);
                    Usuario usuario;
                    try {
                        usuario = usuarioRepository.getUsuario();

                        CentralData centralData = new CentralData(usuario.getNombre(), usuario.getApellido(), token);

                        Log.i("CentralData", centralData.toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        CentralAPI centralAPI = retrofit.create(CentralAPI.class);
                        Call call = centralAPI.vincularCentral(centralData);
                        Log.i("Response", call.toString());
                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {
                                try {
                                    if (centralViewModel.getCentral(central) == null) {
                                        centralViewModel.insert(central);
                                        bundle.putString("descripcion", "Vinculacion con central " + central + " exitosa");
                                    } else {
                                        bundle.putString("descripcion", "La central " + central + " ya existe");
                                    }
                                    mOnCompleteListener.onComplete(bundle);
                                } catch (SQLiteConstraintException e) {
                                    bundle.putString("descripcion", "La central " + central + " ya existe");
                                    mOnCompleteListener.onComplete(bundle);
                                } catch (InterruptedException | ExecutionException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Log.i("Error", t.getMessage());
                                bundle.putString("descripcion", "La central " + central + " no pudo ser vinculada");
                                mOnCompleteListener.onComplete(bundle);
                            }
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

}
