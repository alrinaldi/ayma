package com.example.aoma;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
 ZXingScannerView ScannerView;
 Button simpan_button;
  @Override
  protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      ScannerView = new ZXingScannerView(this);
      setContentView(ScannerView);
  }

    @Override
    public void handleResult(Result result) {
QrActivity.qrscan.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause(){
      super.onPause();
      ScannerView.stopCamera();
    }
    @Override
    protected void onResume(){
      super.onResume();
      ScannerView.setResultHandler(this);
      ScannerView.startCamera();
    }

}
