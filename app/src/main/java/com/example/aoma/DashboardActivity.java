package com.example.aoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
 Button btnSet,btnQr,btnMs,btnAn,btnEdu,btnPrf;
 String nrpu;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        btnSet = (Button) findViewById(R.id.buttonSet);
        btnQr= (Button) findViewById(R.id.buttonQr);
        btnMs=(Button) findViewById(R.id.buttonMr);
        btnAn=(Button) findViewById(R.id.buttonAn);
        btnEdu=(Button) findViewById(R.id.buttonpdf);
        btnPrf = (Button) findViewById(R.id.buttonPA);


        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingview();
            }
        });

        btnEdu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                eduview();
            }
        });
        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrview();
            }
        });
        btnAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysisview();
            }
        });
        btnMs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            masterview();
            }
        });
        /*
        btnPrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    profileAsset();

            }
        });

         */
    }


    private void qrview(){
            Intent intent = new Intent(DashboardActivity.this, QrActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    private void masterview(){
        Intent intent = new Intent(DashboardActivity.this, MasterDataActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    private void analysisview(){
      Intent intent =  new Intent(DashboardActivity.this, ViewResultActivity.class);
        intent.putExtra("nrp",nrpu);
      startActivity(intent);
      finish();
    }
    private void settingview(){
        Intent intent =  new Intent(DashboardActivity.this, SettingActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    private void eduview(){
        Intent intent =  new Intent(DashboardActivity.this,EducationMenu.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    /*
    private void profileAsset(){
        Intent intent =  new Intent(DashboardActivity.this,ProfileScan.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    */

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }

}
