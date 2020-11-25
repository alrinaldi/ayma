package com.example.aoma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoma.api.Config;
import com.example.aoma.api.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MasterDataActivity extends AppCompatActivity {

    private String JSON_STRING;
    String nrpu;
    ListView listView;
    @Override

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        setContentView(R.layout.activity_master);
        listView = (ListView) findViewById(R.id.listView);
        getJSON();

    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("data");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nomerasset = jo.getString(Config.TAG_NOMERASSETBARU);
                String name = jo.getString(Config.TAG_DESKRIPSI);
                String wct = jo.getString(Config.TAG_WCT);

                HashMap<String,String> master = new HashMap<>();
                master.put(Config.TAG_NOMERASSETBARU,nomerasset);
                master.put(Config.TAG_DESKRIPSI,name);
                master.put(Config.TAG_WCT,wct);
                list.add(master);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                MasterDataActivity.this, list, R.layout.view_master,
                new String[]{Config.TAG_NOMERASSETBARU,Config.TAG_DESKRIPSI,Config.TAG_WCT},
                new int[]{R.id.nomerAsset, R.id.deskAsset, R.id.wct});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MasterDataActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.VIEW_MASTER_URL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent (MasterDataActivity.this,DashboardActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}
