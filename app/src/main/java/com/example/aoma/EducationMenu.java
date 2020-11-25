package com.example.aoma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EducationMenu extends AppCompatActivity {
    Button btnSkd,btnIk,btnPsr;
    String nrpu;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.education);

        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        btnIk = (Button) findViewById(R.id.ikasset);
        btnPsr= (Button) findViewById(R.id.prosedurasset);
        btnSkd=(Button) findViewById(R.id.skdasset);



        btnSkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skdview();
            }
        });

  btnIk.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          ikview();
      }
  });
        btnPsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             psrview();
            }
        });




    }

    private void skdview(){
        Intent intent =  new Intent(EducationMenu.this, SkdView.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    private void ikview(){
        Intent intent = new Intent(EducationMenu.this, IkView.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
    private void psrview(){
        Intent intent =  new Intent(EducationMenu.this, PsrView.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent intent = new Intent (EducationMenu.this,DashboardActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}
