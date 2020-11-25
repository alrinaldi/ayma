package com.example.aoma.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aoma.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

private ArrayList<DataAsset> arrayList = new ArrayList<>();

public RecyclerAdapter(ArrayList<DataAsset> arrayList){
    this.arrayList = arrayList;
}

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_asset,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
holder.input_nomor.setText(arrayList.get(position).getNomerassetbaru());
holder.input_wct.setText(arrayList.get(position).getWorkcenter());
holder.input_deskripsi.setText(arrayList.get(position).getDeskripsi());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    MaterialEditText input_deskripsi,input_nomor,input_wct;

        public MyViewHolder(View itemView) {
            super(itemView);
            input_deskripsi = (MaterialEditText) itemView.findViewById(R.id.input_deskripsi);
            input_nomor = (MaterialEditText) itemView.findViewById(R.id.input_nomor);
            input_wct = (MaterialEditText) itemView.findViewById(R.id.input_wct);
        }
    }
}
