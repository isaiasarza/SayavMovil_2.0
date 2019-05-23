package com.sayav.desarrollo.sayav20;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralListAdapter;
import com.sayav.desarrollo.sayav20.central.CentralViewModel;

import java.util.List;

import static com.sayav.desarrollo.sayav20.R.layout.activity_centrales;

public class CentralesActivity extends MenuActivity implements VincularDialog.OnComplete {

    private static final int NEW_CENTRAL_ACTIVITY_REQUEST_CODE = 1;
    private static final String EXTRA_REPLY = "com.example.android.centrallistsql.REPLY";
    private CentralViewModel mCentralViewModel;
    private MyFirebaseIDService firebase;
    private DialogFragment vincularFragment;
    RecyclerView recyclerView;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_centrales);
        firebase = new MyFirebaseIDService(this);

        mCentralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);
        final CentralListAdapter adapter = new CentralListAdapter(getApplicationContext());

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCentralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vincularFragment = new VincularDialog();
                vincularFragment.show(getSupportFragmentManager(), "vincular");
            }
        });

        mCentralViewModel.getAllCentrals().observe(this, new Observer<List<Central>>() {
            @Override
            public void onChanged(@Nullable final List<Central> centrales) {
                // Update the cached copy of the words in the adapter.
               // RecyclerView recyclerView = findViewById(R.id.recyclerview);
                adapter.setCentrales(centrales);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CENTRAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Central central = new Central("isaunp.ddns.net",200);
            mCentralViewModel.insert(central);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void onComplete(Bundle bundle) {
        boolean tokenOnServer = bundle.getBoolean("vincular");
        String central = bundle.getString("central");
        int puerto = bundle.getInt("puerto");
        String text=bundle.getString("descripcion");
        Log.i("onComplete",central);
        Snackbar.make(recyclerView,text,Snackbar.LENGTH_SHORT).show();

    }
}
