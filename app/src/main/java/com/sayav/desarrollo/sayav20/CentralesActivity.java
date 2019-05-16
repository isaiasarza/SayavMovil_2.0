package com.sayav.desarrollo.sayav20;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sayav.desarrollo.sayav20.central.Central;
import com.sayav.desarrollo.sayav20.central.CentralListAdapter;
import com.sayav.desarrollo.sayav20.central.CentralViewModel;

import java.util.List;

public class CentralesActivity extends AppCompatActivity {

    private static final int NEW_CENTRAL_ACTIVITY_REQUEST_CODE = 4;
    private static final String EXTRA_REPLY = "com.example.android.centrallistsql.REPLY";
    private CentralViewModel mCentralViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centrales);
        mCentralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);
        final CentralListAdapter adapter = new CentralListAdapter(getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mCentralViewModel = ViewModelProviders.of(this).get(CentralViewModel.class);

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
}
