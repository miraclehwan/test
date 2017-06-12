package mobile.com.prototype_socialapp.MainFragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import mobile.com.prototype_socialapp.Main3_ListView.VideoAdapter;
import mobile.com.prototype_socialapp.Main3_ListView.VideoData;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardAdapter;
import mobile.com.prototype_socialapp.Menu4_ListView.BoardData;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-09.
 * 메인메뉴3 Fragment
 */

public class Main3 extends Fragment{

    VideoView Video;
    TextView Title;
    ListView listView;
    ArrayList<VideoData> datas = new ArrayList<>();
    VideoAdapter adapter;

    int CurrnetPosition = 9123470;

    ArrayList<String> UserList = new ArrayList<>();
    ArrayList<String> TitleList = new ArrayList<>();
    ArrayList<String> DateList = new ArrayList<>();
    ArrayList<String> CountList = new ArrayList<>();
    ArrayList<String> VideoUrl = new ArrayList<>();
    ArrayList<String> ImageList = new ArrayList<>();


    MediaPlayer mediaPlayer = null;

    public Main3(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main3, container, false);

        Video = (VideoView) layout.findViewById(R.id.Menu3_Video);
        Title = (TextView) layout.findViewById(R.id.Menu3_VideoTitle);
        listView = (ListView) layout.findViewById(R.id.Menu3_VideoListView);

        Title.setText("경기영상을 선택해주세요.");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position!=CurrnetPosition){
                    PlayVideo(VideoUrl.get(position));
                    CurrnetPosition = position;
                    Title.setText(DateList.get(position).substring(0,4) +"." + DateList.get(position).substring(4,6) + "."+ DateList.get(position).substring(6,8));
                    addCount("http://miraclehwan.vps.phps.kr/SS/VideoAddCount.php", VideoUrl.get(position));
                }

            }
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        UserList.clear();
        DateList.clear();
        TitleList.clear();
        VideoUrl.clear();
        CountList.clear();
        ImageList.clear();
        datas.clear();
        VideoRequest("http://miraclehwan.vps.phps.kr/SS/VideoListRequest.php");
    }

    void setApater(){


        for (int i=0; i < UserList.size(); i++){
            datas.add(new VideoData(UserList.get(i), TitleList.get(i), DateList.get(i), CountList.get(i), VideoUrl.get(i), ImageList.get(i)));
        }

        adapter = new VideoAdapter(getActivity().getLayoutInflater(), datas);

        listView.setAdapter(adapter);

    }

    private void PlayVideo(String VideoURL){
        MediaController mediaController = new MediaController(getContext());

        mediaController.setAnchorView(Video);

        Video.setVideoURI(Uri.parse("http://miraclehwan.vps.phps.kr/SS/video/" + VideoURL));
        Video.setMediaController(mediaController);
        Video.requestFocus();
        Video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mp.start();
            }
        });
    }

    public void VideoRequest(String uri) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                Log.e("JSON Log : ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        UserList.add(jsonObject.getString("user"));
                        DateList.add(jsonObject.getString("date"));
                        TitleList.add(jsonObject.getString("title"));
                        CountList.add(jsonObject.getString("count"));
                        VideoUrl.add(jsonObject.getString("videourl"));
                        ImageList.add(jsonObject.getString("imageurl"));
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


    public void addCount(final String uri, final String videourl){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                UserList.clear();
                DateList.clear();
                TitleList.clear();
                VideoUrl.clear();
                CountList.clear();
                ImageList.clear();
                datas.clear();
                VideoRequest("http://miraclehwan.vps.phps.kr/SS/VideoListRequest.php");
                adapter.notifyDataSetChanged();
            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("videourl", "UTF-8") + "=" + URLEncoder.encode(videourl, "UTF-8");

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
        getdatajson.execute(uri, videourl);

    }

}
