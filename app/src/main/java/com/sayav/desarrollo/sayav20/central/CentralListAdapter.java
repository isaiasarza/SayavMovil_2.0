package com.sayav.desarrollo.sayav20.central;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sayav.desarrollo.sayav20.R;

import java.util.List;

public class CentralListAdapter extends RecyclerView.Adapter<CentralListAdapter.CentralViewHolder> {

    class CentralViewHolder extends RecyclerView.ViewHolder {
        private final TextView subdominio;
        private final TextView puerto;


        private CentralViewHolder(View itemView) {
            super(itemView);
            subdominio = itemView.findViewById(R.id.subdominio);
            puerto = itemView.findViewById(R.id.puerto);
        }
    }

    private final LayoutInflater mInflater;
    private List<Central> mCentrales;



    public CentralListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CentralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CentralViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CentralViewHolder holder, int position) {
        if (mCentrales != null) {
            Central current = mCentrales.get(position);
            Log.i("Central", current.toString());
            holder.subdominio.setText(current.getSubdominio());
            holder.puerto.setText(Integer.toString(current.getPuerto()));
        } else {
            // Covers the case of data not being ready yet.
            holder.subdominio.setText("No hay subdominio");
            holder.puerto.setText("No hay puerto");
        }
    }

    public void setCentrales(List<Central> centrales){
        mCentrales = centrales;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCentrales has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCentrales != null)
            return mCentrales.size();
        else return 0;
    }
}
