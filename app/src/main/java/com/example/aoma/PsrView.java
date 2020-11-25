package com.example.aoma;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class PsrView extends AppCompatActivity {
    String nrpu;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nrpu = intent.getStringExtra("nrp");
        setContentView(R.layout.psrview);
        PDFView pdfView = findViewById(R.id.pdfpsr);

        pdfView.fromAsset("psr.pdf")
                .enableSwipe(true)
                .load();

    }

    public void onBackPressed(){
        Intent intent = new Intent (PsrView.this,EducationMenu.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}
