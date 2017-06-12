package mobile.com.prototype_socialapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Daehwan Kim on 2017-06-10.
 * 게시판 글쓰기
 */

public class Write extends AppCompatActivity {

    String imageURL = null;

    ImageView BackButton;
    TextView OKButton;
    EditText Title;
    EditText Content;
    SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        BackButton = (ImageView) findViewById(R.id.Write_BackButton);
        OKButton = (TextView) findViewById(R.id.Write_OKButton);
        Title = (EditText) findViewById(R.id.Write_Title);
        Content = (EditText) findViewById(R.id.Write_Content);
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Title.getText().toString().length()>0 && Content.getText().toString().length()>0){
                    getImageURL("http://miraclehwan.vps.phps.kr/SS/getImageURL.php", pref.getString("HiddenID", ""));
                }
            }
        });
    }

    public void Insert(final String uri, final String user, final String title, final String content, final String currenttime, final String reply, final String count, final String imageurl){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Write Log", s);
                if (Boolean.parseBoolean(s)){
                    Toast.makeText(Write.this, "등록완료", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Write.this, "등록실패", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    Log.e("Write Log", uri);
                    Log.e("Write Log", user);
                    Log.e("Write Log", title);
                    Log.e("Write Log", content);
                    Log.e("Write Log", currenttime);
                    Log.e("Write Log", reply);
                    Log.e("Write Log", count);
                    Log.e("Write Log", imageurl);

                    String data  = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
                    data += "&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8");
                    data += "&" + URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
                    data += "&" + URLEncoder.encode("currenttime", "UTF-8") + "=" + URLEncoder.encode(currenttime, "UTF-8");
                    data += "&" + URLEncoder.encode("reply", "UTF-8") + "=" + URLEncoder.encode(reply, "UTF-8");
                    data += "&" + URLEncoder.encode("count", "UTF-8") + "=" + URLEncoder.encode(count, "UTF-8");
                    data += "&" + URLEncoder.encode("imageurl", "UTF-8") + "=" + URLEncoder.encode(imageurl, "UTF-8");

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null){
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

        }
        GetDataJSON getdatajson = new GetDataJSON();
        getdatajson.execute(uri, user, title, content, currenttime, reply);

    }


    public void getImageURL(final String uri, final String user){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Write Log", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        imageURL = (jsonObject.getString("pic"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Date date = new Date();
                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmm");

                Insert("http://miraclehwan.vps.phps.kr/SS/Insert_Board.php", pref.getString("HiddenID", ""), Title.getText().toString(), Content.getText().toString(), day.format(date), "0", "0", imageURL);


            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null){
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }

        }
        GetDataJSON getdatajson = new GetDataJSON();
        getdatajson.execute(uri, user);

    }

}

