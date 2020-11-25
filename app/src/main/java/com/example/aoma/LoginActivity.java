
package com.example.aoma;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class LoginActivity extends AppCompatActivity {
EditText nrpe,passworde;
Button loginb;
AppCompatButton logina;
AwesomeText ImgShowHidePassword;
Boolean pwd_status = true;
private ProgressDialog pDialog;
private Context context;
String nrpu;

@Override
    public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    context=LoginActivity.this;

    pDialog =  new ProgressDialog(context);
    nrpe=(EditText) findViewById(R.id.nrp);
    passworde=(EditText) findViewById(R.id.password);
    loginb=(Button) findViewById(R.id.signin);

    loginb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendLogin();
        }
    });

    ImgShowHidePassword = (AwesomeText) findViewById(R.id.ImgShowPassword);

    ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View v) {

                if (pwd_status) {
                    passworde.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    passworde.setSelection(passworde.length());
                } else {
                    passworde.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    passworde.setSelection(passworde.length());
                }

        }
    });

}
    private void sendLogin() {
        // Setting POST request ke server
        StringRequest loginRequest = new StringRequest(Request.Method.POST, "http://192.168.2.2/aoma/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response dari server ketika sukses dengan mengkonvert menjadi JSON
                        try {
                            JSONObject json = new JSONObject(response);
                            // Mengambil variable status pada response
                            String status = json.getString("status");
                            String nrp = json.getString("nrp");

                            if(status.equals("success")){
                                // Jika Login Sukses Maka pindah ke activity lain.
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                intent.putExtra("nrp",nrp);
                                startActivity(intent);

                            }else{
                                // Jika Login Gagal Maka mengeluarkan Toast dengan message.
                                Toast.makeText(getApplicationContext(), "Username & Password Salah", Toast.LENGTH_LONG).show();
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
                params.put("nrp", nrpe.getText().toString());
                params.put("password", passworde.getText().toString());
                return params;
            }
        };
        // Buat antrian request pada cache android
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Tambahkan Request pada antrian request
        requestQueue.add(loginRequest);
    }

    public static class SettingActivity {
    }

    @Override
    public void onBackPressed(){
    finish();
    System.exit(0);
    }
}
