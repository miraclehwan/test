package mobile.com.prototype_socialapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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
import java.util.ArrayList;
import java.util.Date;

import mobile.com.prototype_socialapp.Comment_ListView.CommentAdapter;
import mobile.com.prototype_socialapp.Comment_ListView.CommentData;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardAdapter;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardData;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class BoardContent extends AppCompatActivity {

    String imageURL = null;
    SharedPreferences pref;
    EditText Comment;
    Intent intent;

    ArrayList<String> IdxList = new ArrayList<>();
    ArrayList<String> UserLIst = new ArrayList<>();
    ArrayList<String> CommentList = new ArrayList<>();
    ArrayList<String> DateList = new ArrayList<>();
    ArrayList<String> ImageURLList = new ArrayList<>();

    TextView CommentAddButton;

    ListView listView;
    ArrayList<CommentData> datas = new ArrayList<>();
    CommentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_boardcontent);

        intent = getIntent();

        TextView Title = (TextView) findViewById(R.id.BoardContent_Title);
        ImageView Image = (ImageView) findViewById(R.id.BoardContent_Image);
        TextView User = (TextView) findViewById(R.id.BoardContent_User);
        TextView Date = (TextView) findViewById(R.id.BoardContent_Date);
        TextView Content = (TextView) findViewById(R.id.BoardContent_Content);
        TextView Reply = (TextView) findViewById(R.id.BoardContent_Reply);
        ImageView BackButton = (ImageView) findViewById(R.id.BoardContent_BackButton);
        Comment = (EditText) findViewById(R.id.BoardContent_Comment);
        CommentAddButton = (TextView) findViewById(R.id.BoardContent_CommentAddButton);
        listView = (ListView) findViewById(R.id.BoardContent_ListView);
        pref = getSharedPreferences("pref", MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Image.setBackground(new ShapeDrawable(new OvalShape()));
            Image.setClipToOutline(true);
        }

        Title.setText(intent.getStringExtra("title"));
        Glide.with(this).load("http://miraclehwan.vps.phps.kr/SS/pic/" + intent.getStringExtra("imageurl")).into(Image);
        int idx = intent.getStringExtra("user").indexOf("@");
        User.setText(intent.getStringExtra("user").substring(0,idx));
        Date.setText(
                intent.getStringExtra("date").substring(0,4) +"." +
                        intent.getStringExtra("date").substring(4,6) + "."+
                        intent.getStringExtra("date").substring(6,8) + " " +
                        intent.getStringExtra("date").substring(8,10) + ":" +
                        intent.getStringExtra("date").substring(10,12) + " | " +
                        "조회 " + String.valueOf((Integer.parseInt(intent.getStringExtra("count")))+1));
        Content.setText("\n" + intent.getStringExtra("content") + "\n\n");
        Reply.setText("댓글 " + intent.getStringExtra("reply"));


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CommentAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageURL("http://miraclehwan.vps.phps.kr/SS/getImageURL.php", pref.getString("HiddenID", ""));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        IdxList.clear();
        UserLIst.clear();
        CommentList.clear();
        DateList.clear();
        ImageURLList.clear();
        datas.clear();
        CommentListRequest("http://miraclehwan.vps.phps.kr/SS/CommentListRequest.php", intent.getStringExtra("idx"));
    }

    void setApater(){


        for (int i=0; i < IdxList.size(); i++){
            datas.add(new CommentData(IdxList.get(i), UserLIst.get(i), CommentList.get(i), DateList.get(i), ImageURLList.get(i)));
        }

        adapter = new CommentAdapter(getLayoutInflater(), datas);

        listView.setAdapter(adapter);
        listViewHeightSet(adapter, listView);
    }

    private static void listViewHeightSet(BaseAdapter listAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public void Insert(final String uri, final String idx, final String user, final String comment, final String currenttime, final String imageurl){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Write Log", s);
                if (Boolean.parseBoolean(s)){
                    addCount("http://miraclehwan.vps.phps.kr/SS/CommentAddCount.php", intent.getStringExtra("idx"));

                }else{
                    Toast.makeText(BoardContent.this, "등록실패", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("idx", "UTF-8") + "=" + URLEncoder.encode(idx, "UTF-8");
                    data += "&" + URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
                    data += "&" + URLEncoder.encode("comment", "UTF-8") + "=" + URLEncoder.encode(comment, "UTF-8");
                    data += "&" + URLEncoder.encode("currenttime", "UTF-8") + "=" + URLEncoder.encode(currenttime, "UTF-8");
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
        getdatajson.execute(uri, idx, user, comment, currenttime, imageurl);

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

                Insert("http://miraclehwan.vps.phps.kr/SS/Insert_Comment.php", intent.getStringExtra("idx") ,pref.getString("HiddenID", ""), Comment.getText().toString(), day.format(date), imageURL);


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

    public void CommentListRequest(final String uri, final String idx){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Write Log", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        IdxList.add(jsonObject.getString("idx"));
                        UserLIst.add(jsonObject.getString("user"));
                        CommentList.add(jsonObject.getString("comment"));
                        DateList.add(jsonObject.getString("currenttime"));
                        ImageURLList.add(jsonObject.getString("imageurl"));
                    }
                    setApater();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("idx", "UTF-8") + "=" + URLEncoder.encode(idx, "UTF-8");

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
        getdatajson.execute(uri, idx);

    }

    public void addCount(final String uri, final String idx){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(BoardContent.this, "등록완료", Toast.LENGTH_SHORT).show();
                Intent re = new Intent(BoardContent.this, BoardContent.class);
                re.putExtra("idx", intent.getStringExtra("idx"));
                re.putExtra("user", intent.getStringExtra("user"));
                re.putExtra("date", intent.getStringExtra("date"));
                re.putExtra("title", intent.getStringExtra("title"));
                re.putExtra("content", intent.getStringExtra("content"));
                int temp = Integer.parseInt(intent.getStringExtra("reply"));
                temp = temp+1;
                re.putExtra("reply", String.valueOf(temp));
                re.putExtra("count", intent.getStringExtra("count"));
                re.putExtra("imageurl", intent.getStringExtra("imageurl"));
                startActivity(re);
                finish();
                overridePendingTransition(0, 0);

            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("idx", "UTF-8") + "=" + URLEncoder.encode(idx, "UTF-8");

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
        getdatajson.execute(uri, idx);

    }

}
