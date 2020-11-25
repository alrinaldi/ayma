package com.example.aoma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aoma.api.Config;
import com.example.aoma.api.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    private JSONArray result;

    Spinner spinner;
    Button submit;
    private ArrayList<String> arrayList;
    String nama;
    String name;
    TextView textnama,partner;
    String nrpu;

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_partner);
        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        spinner = (Spinner) findViewById(R.id.spinner1);
        textnama = (TextView) findViewById(R.id.textnama);
        submit = (Button) findViewById(R.id.submit);
        partner = (TextView) findViewById(R.id.partner);
        getPartner();
        arrayList = new ArrayList<String>();
        getdata();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Setting the values to textviews for a selected item
               // employeename.setText(getemployeeName(position));
                nama = arrayList.get(position);
               // textnama.setText(getemployeeName(position));
                //mailid.setText(getmailid(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                nama = "";
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePartner();
                Intent intent =  new Intent(SettingActivity.this, DashboardActivity.class);
                intent.putExtra("nrp",nrpu);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed(){
        Intent intent =  new Intent(SettingActivity.this, DashboardActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }

    private void getdata() {
        StringRequest stringRequest = new StringRequest("http://192.168.2.2/aoma/list_partner.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray("data");
                            empdetails(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void empdetails(JSONArray j) {
        for (int i = 0; i < j.length(); i++) {
            try {
                JSONObject json = j.getJSONObject(i);
                arrayList.add(json.getString("nama"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // arrayList.add(0,"Select Employee");
        spinner.setAdapter(new ArrayAdapter<String>(SettingActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList));
    }
    //Method to get student name of a particular position
    private String getemployeeName(int position){
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);
            //Fetching name from that object
            name = json.getString("nama");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }
    //Doing the same with this method as we did with getName()

    private void updatePartner() {
        // String lblt = checklabel;
        //String cktkn = checkkon;

        // Log.i("TES",checkkon);
        //final String l = editTextSalary.getText().toString().trim();

        class UpdatePartner extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SettingActivity.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(SettingActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("nama", nama);
                //hashMap.put("kondisi", cktkn);
                // hashMap.put(konfigurasi.KEY_EMP_GAJIH,salary);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.UPDATE_PARTNER_URL, hashMap);
                return s;
            }
        }

        UpdatePartner up = new UpdatePartner();
        up.execute();
    }


    private void getPartner() {

        class GetPartner extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(SettingActivity.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                // showEmployee(s);
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.VIEW_PARTNER_AKRIF);
                return s;
            }
        }
        GetPartner gp = new GetPartner();
        gp.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray result = jsonObject.getJSONArray("data");
            JSONObject c = result.getJSONObject(0);
            String namapartner = c.getString("namapartner");

            //  String nomerasset = c.getString("TES");

            partner.setText(namapartner);
           // partner.setText(c.getString(namapartner));
            //Log.i(String.valueOf(input_deskripsi),"wct");
            //String.valueOf(input_wct);
            // Log.i(lbl,lbl);
            //editTextSalary.setText(sal);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
