package com.example.aoma.api;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aoma.R;

/**
 * Created by Belal on 7/22/2015.
 */
public class CustomList extends ArrayAdapter<String> {
    private String[] urls,deskripsis,nomerassetbarus,tanggals,labels,kondisis;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] urls,String[] nomerassetbarus,String[] deskripsis,String[] tanggals, String[] labels,String[] kondisis,Bitmap[] bitmaps) {
        super(context, R.layout.view_result, urls);
        this.context = context;
        this.nomerassetbarus = nomerassetbarus;
        this.deskripsis = deskripsis;
        this.tanggals = tanggals;
        this.labels = labels;
        this.kondisis = kondisis;
        this.urls= urls;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.view_result, null, true);
        TextView Tnomerasset = (TextView) listViewItem.findViewById(R.id.noAsset);
        TextView Tdeskripsi = (TextView) listViewItem.findViewById(R.id.deskripsi);
        TextView Ttanggal = (TextView) listViewItem.findViewById(R.id.assetTgl);
        TextView Tlabel = (TextView) listViewItem.findViewById(R.id.label);
        TextView Tkondisi = (TextView) listViewItem.findViewById(R.id.kondisi);
        ImageView TimgAsset = (ImageView) listViewItem.findViewById(R.id.imgAsset);

        Tnomerasset.setText(nomerassetbarus[position]);
        Tdeskripsi.setText(deskripsis[position]);
        Ttanggal.setText(tanggals[position]);
        Tlabel.setText(labels[position]);
        Tkondisi.setText(kondisis[position]);
        if(bitmaps[position].equals("")){
            TimgAsset.setImageResource(R.drawable.imgnf);
        }else {
            TimgAsset.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position], 300, 200, true));
        }
        return  listViewItem;
    }
}