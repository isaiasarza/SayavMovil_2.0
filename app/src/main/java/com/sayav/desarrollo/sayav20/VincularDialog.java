package com.sayav.desarrollo.sayav20;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralViewModel;

public class VincularDialog extends DialogFragment {
    private String subdominio;
    private String puerto;
    private CentralViewModel centralViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mView = inflater.inflate(R.layout.dialog_vincular, null);
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
                        Central central = new Central(subdominio,Integer.valueOf(puerto).intValue());
                        Log.i("Vinculando", central.toString());
                        centralViewModel.insert(central);
                        //Snackbar.make(mView,"Vinculando Central " + subdominio ,Snackbar.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VincularDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
