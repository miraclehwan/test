package mobile.com.prototype_socialapp.MainFragment;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.ArrayList;

import mobile.com.prototype_socialapp.BoardContent;
import mobile.com.prototype_socialapp.Join;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardAdapter;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardData;
import mobile.com.prototype_socialapp.R;
import mobile.com.prototype_socialapp.Write;

/**
 * Created by Daehwan Kim on 2017-06-09.
 * 메인메뉴4 Fragment
 */

public class Main4 extends Fragment{

    ArrayList<String> IdxList = new ArrayList<>();
    ArrayList<String> UserList = new ArrayList<>();
    ArrayList<String> DateList = new ArrayList<>();
    ArrayList<String> TitleList = new ArrayList<>();
    ArrayList<String> ContentList = new ArrayList<>();
    ArrayList<String> ReplyList = new ArrayList<>();
    ArrayList<String> CountList = new ArrayList<>();
    ArrayList<String> ImageURLList = new ArrayList<>();

    ListView listView;
    ArrayList<BoardData> datas = new ArrayList<>();
    BoardAdapter adapter;

    public Main4(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main4, container, false);

        ImageView WriteButton = (ImageView) layout.findViewById(R.id.Menu4_Write);

        WriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MoveWriteActivity = new Intent(getActivity(), Write.class);
                startActivity(MoveWriteActivity);
            }
        });

        listView = (ListView) layout.findViewById(R.id.Write_ListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                addCount("http://miraclehwan.vps.phps.kr/SS/BoardAddCount.php", IdxList.get(position));
                Intent MoveBoardContentActivity = new Intent(getActivity(), BoardContent.class);

                MoveBoardContentActivity.putExtra("idx", IdxList.get(position));
                MoveBoardContentActivity.putExtra("user", UserList.get(position));
                MoveBoardContentActivity.putExtra("date", DateList.get(position));
                MoveBoardContentActivity.putExtra("title", TitleList.get(position));
                MoveBoardContentActivity.putExtra("content", ContentList.get(position));
                MoveBoardContentActivity.putExtra("reply", ReplyList.get(position));
                MoveBoardContentActivity.putExtra("count", CountList.get(position));
                MoveBoardContentActivity.putExtra("imageurl", ImageURLList.get(position));

                startActivity(MoveBoardContentActivity);
            }
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        IdxList.clear();
        UserList.clear();
        DateList.clear();
        TitleList.clear();
        ContentList.clear();
        ReplyList.clear();
        CountList.clear();
        ImageURLList.clear();
        datas.clear();
        BoardListRequest("http://miraclehwan.vps.phps.kr/SS/BoardListRequest.php");
    }

    void setApater(){


        for (int i=0; i < UserList.size(); i++){
            datas.add(new BoardData(UserList.get(i), DateList.get(i), TitleList.get(i), ContentList.get(i), ReplyList.get(i), CountList.get(i), ImageURLList.get(i)));
        }

        adapter = new BoardAdapter(getActivity().getLayoutInflater(), datas);

        listView.setAdapter(adapter);
    }

    public void BoardListRequest(String uri) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                Log.e("JSON Log : ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        IdxList.add(jsonObject.getString("idx"));
                        UserList.add(jsonObject.getString("user"));
                        DateList.add(jsonObject.getString("day"));
                        TitleList.add(jsonObject.getString("title"));
                        ContentList.add(jsonObject.getString("content"));
                        ReplyList.add(jsonObject.getString("reply"));
                        CountList.add(jsonObject.getString("count"));
                        ImageURLList.add(jsonObject.getString("imageurl"));
                    }
                    setApater();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];


                try {

                    URL url = new URL(uri);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoOutput(true);

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json + "\n");
                    }

                    return stringBuilder.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetDataJSON getdatajson = new GetDataJSON();
        getdatajson.execute(uri);

    }


    public void addCount(final String uri, final String idx){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);

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
