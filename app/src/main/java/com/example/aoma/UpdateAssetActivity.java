package com.example.aoma;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.aoma.api.Config;
import com.example.aoma.api.RequestHandler;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UpdateAssetActivity  extends AppCompatActivity {
    private static final String TAG = UpdateAssetActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST_CODE = 7777;
    private Bitmap bitmap,bmp,decoded;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private Uri filePath;
    String nomerassetbaru,nrpu;
    String wct;
    String desk;
    public  static final int RequestPermissionCode  = 1 ;
    ProgressDialog progressDialog ;
    String checklabel,lbl,kondisi,checkkon;
    MaterialEditText input_nomer, input_wct;
    EditText input_deskripsi;
    Button buttonUpdate1,tkpicture;
    RadioGroup opsil,opsi;
    RadioButton no,ok,ug,nug,mt,nup;
    ImageView fotolm,fotobr;
    boolean check = true;
    private Uri mPicCaptureUri = null;
  //  String ImageNameFieldOnServer = "fotobaru" ;
   // String ImagePathFieldOnServer = "foto" ;
  //  String ImageUploadPathOnSever ="https://androidjsonblog.000webhostapp.com/capture_img_upload_to_server.php" ;

  String GetImageNameFromEditText;
    String ImageNameFieldOnServer = "image_tag" ;
    String ImagePathFieldOnServer = "image_data" ;
    String ImageUploadPathOnSever = "http://192.168.2.2/aoma/upload_foto.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_asset);

        Intent intent = getIntent();
        nomerassetbaru = intent.getStringExtra(Config.NOMERASSET);
        nrpu = intent.getStringExtra("nrp");
        input_nomer = (MaterialEditText) findViewById(R.id.input_nomor);
        input_wct = (MaterialEditText) findViewById(R.id.input_wct);
        input_deskripsi = (EditText) findViewById(R.id.input_deskripsi);
        buttonUpdate1 = (Button) findViewById(R.id.button_update);
        buttonUpdate1.setEnabled(false);
        opsil = (RadioGroup) findViewById(R.id.opsil);
        opsi = (RadioGroup) findViewById(R.id.opsi);
        no = (RadioButton) findViewById(R.id.nok);
        ok = (RadioButton) findViewById(R.id.lok);
        ug = (RadioButton) findViewById(R.id.ug);
        nug = (RadioButton) findViewById(R.id.nug);
        nup = (RadioButton) findViewById(R.id.nup);
        mt = (RadioButton) findViewById(R.id.mt);
        fotolm = (ImageView) findViewById(R.id.fotolm);
        fotobr = (ImageView) findViewById(R.id.fotobr);
        tkpicture = (Button) findViewById(R.id.tkpicture);
        input_nomer.setText(nomerassetbaru);
        getAsset();
//input_wct.setText(wct);
//input_deskripsi.setText(desk);


        tkpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });

            buttonUpdate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int selectedId = opsil.getCheckedRadioButtonId();
                    if (selectedId == ok.getId()) {
                        checklabel = "Ok";
                        //showToast("Jawaban Kamu : " + rbJawaTimur.getText().toString());
                    } else if (selectedId == no.getId()) {
                        //showToast("Jawaban Kamu : " + rbJawaBarat.getText().toString());
                        checklabel = "Not Ok";
                        //} else if (selectedId == rbJawaTengah.getId()){
                        //showToast("Jawaban Kamu : " + rbJawaTengah.getText().toString());
                    } else {
                        checklabel = "";
                    }

                    int selectedIdo = opsi.getCheckedRadioButtonId();
                    if (selectedIdo == ug.getId()) {
                        checkkon = "Use Good";
                        //showToast("Jawaban Kamu : " + rbJawaTimur.getText().toString());
                    } else if (selectedIdo == mt.getId()) {
                        //showToast("Jawaban Kamu : " + rbJawaBarat.getText().toString());
                        checkkon = "Maintenance";
                    } else if (selectedIdo == nug.getId()) {
                        checkkon = "Not Use Good";
                    } else if (selectedIdo == nup.getId()) {
                        checkkon = "Not Use Poor";
                    } else {
                        checkkon = "";
                    }

                    updateEmployee();
                    GetImageNameFromEditText = nomerassetbaru;
                    ImageUploadToServerFunction();
                    Intent intent = new Intent(UpdateAssetActivity.this, QrActivity.class);
                    intent.putExtra("nrp", nrpu);
                    startActivity(intent);
                    finish();
                }
            });

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case(CAMERA_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK)
                {
                    // result code sama, save gambar ke bitmap
                   // Bitmap bitmap;
                    bitmap = (Bitmap) data.getExtras().get("data");
                   // bmp = bitmap;
                   // fotobr.setImageBitmap(bitmap);
                    setToImageView(getResizedBitmap(bitmap, 512));
                        if(bitmap.equals("")){
                            buttonUpdate1.setEnabled(false);
                        }else{
                            buttonUpdate1.setEnabled(true);
                        }
                }
                break;
        }
    }

    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(UpdateAssetActivity.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(UpdateAssetActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(UpdateAssetActivity.this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        fotobr.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    // Upload captured image online on server function.
    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        // Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
       // decoded.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
       // bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStreamObject.toByteArray()));
        byte[] byteArrayVar = bytes.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                // Showing progress dialog at image upload time.
                progressDialog = ProgressDialog.show(UpdateAssetActivity.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                Toast.makeText(UpdateAssetActivity.this,string1,Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
               // fotobr.setImageResource(android.R.color.transparent);

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageNameFieldOnServer, GetImageNameFromEditText);

                HashMapParams.put(ImagePathFieldOnServer, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ImageUploadPathOnSever, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(UpdateAssetActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(UpdateAssetActivity.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    private void getAsset() {

        class GetAsset extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                loading = ProgressDialog.show(UpdateAssetActivity.this, "Fetching...", "Wait...", false, false);
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
                String s = rh.sendGetRequestParam(Config.UPDATE_ASSET_URL, nomerassetbaru);
                return s;
            }
        }
        GetAsset ga = new GetAsset();
        ga.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray result = jsonObject.getJSONArray("data");
            JSONObject c = result.getJSONObject(0);

            String namal = c.getString("namalama");
            String fotol = c.getString("fotolama");
            String fotob = c.getString("fotobaru");
            //  String nomerasset = c.getString("TES");
             input_deskripsi.setText(c.getString(Config.TAG_DESKRIPSI));
             input_wct.setText(c.getString(Config.TAG_WCT));
             kondisi = (c.getString("kondisi"));
           lbl = c.getString("label");
            Log.i(String.valueOf(input_deskripsi),"wct");
           //String.valueOf(input_wct);
           // Log.i(lbl,lbl);
            //editTextSalary.setText(sal);
        if(lbl.equals("Ok")){
            ok.setChecked(true);
        }else if (lbl.equals("Not Ok")){
            no.setChecked(true);
        }else{
            no.setChecked(false);
            ok.setChecked(false);
        }

        if(kondisi.equals("Use Good")){
            ug.setChecked(true);
        }else if (kondisi.equals("Maintenance")){
            mt.setChecked(true);
        }else if (kondisi.equals("Not Use Good")){
            nug.setChecked(true);
        }else if (kondisi.equals("Not Use Poor")){
            nup.setChecked(true);
        }else{
            ug.setChecked(false);
            mt.setChecked(false);
            nug.setChecked(false);
            nup.setChecked(false);
        }
            Glide.with(getApplicationContext())
                    .load(fotol)
                    .crossFade()
                    //.placeholder(R.mipmap.indomie)
                    .into(fotolm);
            Glide.with(getApplicationContext())
                    .load(fotob)
                    .crossFade()
                    //.placeholder(R.mipmap.newfoto)
                    .into(fotobr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*public void radiocheked(View view) {

        Boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.lok:
                if (checked){
                checklabel = "OK";
                }

                break;
            case R.id.nok:
                if (checked) {
                    checklabel = "No OK";
                }
                break;
        }

    }

     */

   /* public void radiokon(View view) {

        Boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.ug:
                if (checked){
                    checkkon = "Use Good";
                }

                break;
            case R.id.mt:
                if (checked) {
                    checkkon = "Maintenance";
                }
                break;

            case R.id.nup:
                if (checked) {
                    checkkon = "Not Use Poor";
                }
                break;

            case R.id.nug:
                if (checked) {
                    checkkon = "Not Use Good";
                }
                break;
        }

    }*/
    private void updateEmployee() {
         String deksripsit = input_deskripsi.getText().toString().trim();
         String wctt = input_wct.getText().toString().trim();

        // String lblt = checklabel;
         //String cktkn = checkkon;

        // Log.i("TES",checkkon);
        //final String l = editTextSalary.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateAssetActivity.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UpdateAssetActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("nomerassetbaru", nomerassetbaru);
                hashMap.put("nrp",nrpu);
                hashMap.put(Config.KEY_DEKSRIPSI, deksripsit);
                hashMap.put(Config.KEY_WCT, wctt);
                hashMap.put("kondisi", checkkon);
               hashMap.put("label", checklabel);
                //hashMap.put("kondisi", cktkn);
                // hashMap.put(konfigurasi.KEY_EMP_GAJIH,salary);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(Config.UPDATE_ASSET, hashMap);
                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    public void onBackPressed(){
        Intent intent = new Intent (UpdateAssetActivity.this,QrActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}





