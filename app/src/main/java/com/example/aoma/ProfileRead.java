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

public class ProfileRead extends AppCompatActivity {
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
    String checklabelp,lbl,kondisi,checkkonp;
    MaterialEditText input_nomerp, input_wctp;
    EditText input_deskripsip;
   // Button buttonUpdate1,tkpicture;
    RadioGroup opsilp,opsip;
    RadioButton nop,okp,ugp,nugp,mtp,nupp;
    ImageView fotolmp,fotobrp;
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
        setContentView(R.layout.profileread);

        Intent intent = getIntent();
        nomerassetbaru = intent.getStringExtra(Config.NOMERASSET);
        nrpu = intent.getStringExtra("nrp");
        input_nomerp = (MaterialEditText) findViewById(R.id.input_nomor);
        input_wctp = (MaterialEditText) findViewById(R.id.input_wct);
        input_deskripsip = (EditText) findViewById(R.id.input_deskripsi);
        //buttonUpdate1p = (Button) findViewById(R.id.button_update);
//        buttonUpdate1.setEnabled(false);
        opsilp = (RadioGroup) findViewById(R.id.opsil);
        opsip = (RadioGroup) findViewById(R.id.opsi);
        nop = (RadioButton) findViewById(R.id.nok);
        okp = (RadioButton) findViewById(R.id.lok);
        ugp = (RadioButton) findViewById(R.id.ug);
        nugp = (RadioButton) findViewById(R.id.nug);
        nupp = (RadioButton) findViewById(R.id.nup);
        mtp = (RadioButton) findViewById(R.id.mt);
        fotolmp = (ImageView) findViewById(R.id.fotolm);
        fotobrp = (ImageView) findViewById(R.id.fotobr);
        //tkpicture = (Button) findViewById(R.id.tkpicture);
        input_nomerp.setText(nomerassetbaru);
        getAsset();
//input_wct.setText(wct);
//input_deskripsi.setText(desk);

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
                     //   buttonUpdate1.setEnabled(false);
                    }else{
                       // buttonUpdate1.setEnabled(true);
                    }
                }
                break;
        }
    }

    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(ProfileRead.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(ProfileRead.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ProfileRead.this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        fotobrp.setImageBitmap(decoded);
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
                progressDialog = ProgressDialog.show(ProfileRead.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);
                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                Toast.makeText(ProfileRead.this,string1,Toast.LENGTH_LONG).show();
                // Setting image as transparent after done uploading.
                // fotobr.setImageResource(android.R.color.transparent);

            }

            @Override
            protected String doInBackground(Void... params) {

                ProfileRead.ImageProcessClass imageProcessClass = new ProfileRead.ImageProcessClass();

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

                    Toast.makeText(ProfileRead.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ProfileRead.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

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
                loading = ProgressDialog.show(ProfileRead.this, "Fetching...", "Wait...", false, false);
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
            input_deskripsip.setText(c.getString(Config.TAG_DESKRIPSI));
            input_wctp.setText(c.getString(Config.TAG_WCT));
            kondisi = (c.getString("kondisi"));
            lbl = c.getString("label");
            Log.i(String.valueOf(input_deskripsip),"wct");
            //String.valueOf(input_wct);
            // Log.i(lbl,lbl);
            //editTextSalary.setText(sal);
            if(lbl.equals("Ok")){
                okp.setChecked(true);
            }else if (lbl.equals("Not Ok")){
                nop.setChecked(true);
            }else{
                nop.setChecked(false);
                okp.setChecked(false);
            }

            if(kondisi.equals("Use Good")){
                ugp.setChecked(true);
            }else if (kondisi.equals("Maintenance")){
                mtp.setChecked(true);
            }else if (kondisi.equals("Not Use Good")){
                nugp.setChecked(true);
            }else if (kondisi.equals("Not Use Poor")){
                nupp.setChecked(true);
            }else{
                ugp.setChecked(false);
                mtp.setChecked(false);
                nugp.setChecked(false);
                nupp.setChecked(false);
            }
            Glide.with(getApplicationContext())
                    .load(fotol)
                    .crossFade()
                    //.placeholder(R.mipmap.indomie)
                    .into(fotolmp);
            Glide.with(getApplicationContext())
                    .load(fotob)
                    .crossFade()
                    //.placeholder(R.mipmap.newfoto)
                    .into(fotobrp);
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
        String deksripsit = input_deskripsip.getText().toString().trim();
        String wctt = input_wctp.getText().toString().trim();

        // String lblt = checklabel;
        //String cktkn = checkkon;

        // Log.i("TES",checkkon);
        //final String l = editTextSalary.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfileRead.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ProfileRead.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("nomerassetbaru", nomerassetbaru);
                hashMap.put("nrp",nrpu);
                hashMap.put(Config.KEY_DEKSRIPSI, deksripsit);
                hashMap.put(Config.KEY_WCT, wctt);
                hashMap.put("kondisi", checkkonp);
                hashMap.put("label", checklabelp);
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
        Intent intent = new Intent (ProfileRead.this,QrActivity.class);
        intent.putExtra("nrp",nrpu);
        startActivity(intent);
        finish();
    }
}
