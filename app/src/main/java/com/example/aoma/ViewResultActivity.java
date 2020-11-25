package com.example.aoma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aoma.api.CustomList;
import com.example.aoma.api.GetAllImages;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ViewResultActivity extends AppCompatActivity{
    String gambar;
    ListView listview;
    ImageView imgAsset;
    public static final String GET_IMAGE_URL="http://192.168.2.2/aoma/view_asset.php";
    public GetAllImages getAllImages;
    public static final String BITMAP_ID = "BITMAP_ID";
    private String JSON_STRING;
    String nrpu;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        listview = (ListView) findViewById(R.id.listView);
        imgAsset = (ImageView) findViewById(R.id.imgAsset);
        nrpu = intent.getStringExtra("nrp");

        // listview.setOnItemClickListener((AdapterView.OnItemClickListener) this);
       // getJSON();
        getURLs();
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewResultActivity.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(ViewResultActivity.this, GetAllImages.imageURLs,GetAllImages.nomerasset,GetAllImages.deskripsi,GetAllImages.tanggal,GetAllImages.label,GetAllImages.kondisi,GetAllImages.bitmaps);
                listview.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAllImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewResultActivity.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAllImages = new GetAllImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }


   /* private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("data");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nomerasset = jo.getString(Config.TAG_NOMERASSETBARU);
                String tanggal = jo.getString(Config.TAG_TANGGAL);
                String deskripsi = jo.getString(Config.TAG_DESKRIPSI);
                String kondisi = jo.getString(Config.TAG_KONDISI);
                String label = jo.getString(Config.TAG_LABEL);
                gambar = jo.getString(Config.TAG_GAMBAR);

                HashMap<String,String> resultasset = new HashMap<>();
                resultasset.put(Config.TAG_NOMERASSETBARU,nomerasset);
                resultasset.put(Config.TAG_TANGGAL,tanggal);
                resultasset.put(Config.TAG_DESKRIPSI,deskripsi);
                resultasset.put(Config.TAG_KONDISI,kondisi);
                resultasset.put(Config.TAG_LABEL,label);
                resultasset.put("http://192.168.1.2/aoma/foto/"+Config.TAG_GAMBAR,gambar);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewResultActivity.this, list, R.layout.view_result,
                new String[]{Config.TAG_NOMERASSETBARU,Config.TAG_TANGGAL,Config.TAG_DESKRIPSI,Config.TAG_KONDISI,Config.TAG_LABEL,Config.TAG_GAMBAR},
                new int[]{R.id.noAsset, R.id.assetTgl, R.id.deskripsi, R.id.kondisi, R.id.label}
                );

        listview.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewResultActivity.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(Config.VIEW_RESULT_URL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
*/
   @Override
   public void onBackPressed(){
       Intent intent =  new Intent(ViewResultActivity.this, DashboardActivity.class);
       intent.putExtra("nrp",nrpu);
       startActivity(intent);
       finish();
   }

    public static class IkView {
    }
}
