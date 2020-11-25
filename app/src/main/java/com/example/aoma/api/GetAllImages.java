package com.example.aoma.api;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.example.aoma.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Belal on 9/19/2015.
 */
public class GetAllImages {

    public static String[] imageURLs,nomerasset,deskripsi,label,kondisi,tanggal;
    public static Bitmap[] bitmaps;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "fotobaru";
    private String json;
    private JSONArray urls,nomerassetbarus;

    public GetAllImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            if(url.equals("")){
                Resources context = null;
                image = ((BitmapDrawable) getResources().getDrawable(R.drawable.imgnf)).getBitmap();
            }else {
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    private Context getResources() {
        return null;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];
        tanggal = new String[urls.length()];
        imageURLs = new String[urls.length()];
        nomerasset = new String[urls.length()];
        deskripsi = new String[urls.length()];
        label = new String[urls.length()];
        kondisi = new String[urls.length()];

        for(int i=0;i<urls.length();i++){
            deskripsi[i] = urls.getJSONObject(i).getString("deskripsi");
            tanggal[i] = urls.getJSONObject(i).getString("tanggal");
            nomerasset[i] = urls.getJSONObject(i).getString("nomerassetbaru");
            label[i] = urls.getJSONObject(i).getString("label");
            kondisi[i] = urls.getJSONObject(i).getString("kondisi");
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
            /*
            if(bitmaps[i].equals("")){

            }

             */
        }
    }
}