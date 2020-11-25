package com.example.aoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aoma.api.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileScan extends AppCompatActivity {

    public static EditText qrscanp;
    String nomerasset,nrpu;
    Button startscanp,searchqr1p;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilescan);

        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        qrscanp = (EditText) findViewById(R.id.qrscanp);
        startscanp = (Button) findViewById(R.id.startscanp);

        searchqr1p = (Button) findViewById(R.id.searchqrp);

        searchqr1p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent =  new Intent(QrActivity.this,UpdateAssetActivity.class);
                nomerasset = qrscanp.getText().toString();
                sendLogin();
                //  intent.putExtra(Config.NOMERASSET,nomerasset);
                //intent.putExtra("nrp",nrpu);
                //  startActivity(intent);
                //  finish();
            }
        });


        startscanp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScanCodePActivity.class));
            }
        });
    }

    private void sendLogin() {
        // Setting POST request ke server
        StringRequest loginRequest = new StringRequest(Request.Method.POST, "http://192.168.2.2/aoma/cekasset.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response dari server ketika sukses dengan mengkonvert menjadi JSON
                        try {
                            JSONObject json = new JSONObject(response);
                            // Mengambil variable status pada response
                            String status = json.getString("status");
                            String noass = json.getString("nomerasset");

                            if(status.equals("success")){
                                // Jika Login Sukses Maka pindah ke activity lain.
                                Intent intent = new Intent(ProfileScan.this, ProfileRead.class);
                                intent.putExtra(Config.NOMERASSET,noass);
                                intent.putExtra("nrp",nrpu);
                                startActivity(intent);
                                finish();
                            }else{
                                // Jika Login Gagal Maka mengeluarkan Toast dengan message.
                                Toast.makeText(getApplicationContext(), "No asset can't be found or Status Asset Disposal ", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle response dari server ketika gagal
                        Toast.makeText(getApplicationContext(),"The Server Unrechable", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                //params.put("Config.NOMERASSE", passworde.getText().toString());
                params.put(Config.NOMERASSET, nomerasset);
                //intent.putExtra(Config.NOMERASSET,nomerasset);

                return params;
            }
        };
        // Buat antrian request pada cache android
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Tambahkan Request pada antrian request
        requestQueue.add(loginRequest);
    }

    @Override
    public void onBackPressed(){
        Intent intent =  new Intent(ProfileScan.this, DashboardActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}
