package mobile.com.prototype_socialapp.Main1_Fragment;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mobile.com.prototype_socialapp.Join;
import mobile.com.prototype_socialapp.MainFragment.Main1;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class Main1_Cal2 extends Fragment {

    View view;
    static Calendar cal;
    static TextView Date;
    ArrayList<Integer> title_int_date = new ArrayList<Integer>();
    ArrayList<String> title_string_date = new ArrayList<String>();
    ArrayList<TextView> TextList = new ArrayList<>();
    ArrayList<TextView> TextUnderList = new ArrayList<>();

    ArrayList<String> DateList = new ArrayList<>();
    ArrayList<String> TimeList = new ArrayList<>();
    ArrayList<String> NameList = new ArrayList<>();
    ArrayList<String> StateList = new ArrayList<>();

    TextView Time1;
    TextView Time2;
    TextView Time3;
    TextView Time_1;
    TextView Time_2;

    TextView Name1;
    TextView Name2;
    TextView Name3;
    TextView Name_1;
    TextView Name_2;


    TextView State1;
    TextView State2;
    TextView State3;
    TextView State_1;
    TextView State_2;

    Main1_Cal1 main1_cal1;

    public static Main1_Cal2 newInstance(){
        Main1_Cal2 to1 = new Main1_Cal2();
        return to1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cal2, container, false);
        main1_cal1 = Main1_Cal1.newInstance();
        Main1.BackButton.setVisibility(View.VISIBLE);
        Main1.BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main1.BackButton.setVisibility(View.INVISIBLE);
                Main1.fm.beginTransaction().replace(R.id.Main1_Fragement, main1_cal1).commit();
            }
        });
        cal = new GregorianCalendar();
        cal.set(Integer.valueOf(Main1_Cal1.aa), Integer.valueOf(Main1_Cal1.bb)-1, Integer.valueOf(Main1_Cal1.cc));


        ImageView CalBack = (ImageView) view.findViewById(R.id.Cal2_BackButton);
        ImageView CalNext = (ImageView) view.findViewById(R.id.Cal2_NextButton);
        Date = (TextView) view.findViewById(R.id.Cal2_Date);

        Time1 = (TextView) view.findViewById(R.id.Cal2_Time1);
        Time2 = (TextView) view.findViewById(R.id.Cal2_Time2);
        Time3 = (TextView) view.findViewById(R.id.Cal2_Time3);
        Time_1 = (TextView) view.findViewById(R.id.Cal2_Time_1);
        Time_2 = (TextView) view.findViewById(R.id.Cal2_Time_2);

        Name1 = (TextView) view.findViewById(R.id.Cal2_Name1);
        Name2 = (TextView) view.findViewById(R.id.Cal2_Name2);
        Name3 = (TextView) view.findViewById(R.id.Cal2_Name3);
        Name_1 = (TextView) view.findViewById(R.id.Cal2_Name_1);
        Name_2 = (TextView) view.findViewById(R.id.Cal2_Name_2);


        State1 = (TextView) view.findViewById(R.id.Cal2_State1);
        State2 = (TextView) view.findViewById(R.id.Cal2_State2);
        State3 = (TextView) view.findViewById(R.id.Cal2_State3);
        State_1 = (TextView) view.findViewById(R.id.Cal2_State_1);
        State_2 = (TextView) view.findViewById(R.id.Cal2_State_2);

        TextList.add(Time1);TextList.add(Time2);TextList.add(Time3);
        TextList.add(Name1);TextList.add(Name2);TextList.add(Name3);
        TextList.add(State1);TextList.add(State2);TextList.add(State3);

        TextList.add(Time_1);TextList.add(Time_2);
        TextList.add(Name_1);TextList.add(Name_2);
        TextList.add(State_1);TextList.add(State_2);


        cal_day();

        CalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal.add(Calendar.DATE, -1);
                cal_day();
            }
        });

        CalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cal.add(Calendar.DATE, +1);
                cal_day();
            }
        });

        return view;
    }


    private void cal_day(){

        for (int i=0; i<TextList.size(); i++){
            TextList.get(i).setText("-");
            TextList.get(i).setTextColor(Color.GRAY);
        }

        title_int_date.add(0, cal.get(Calendar.YEAR));
        title_int_date.add(1, cal.get(Calendar.MONTH)+1);
        title_int_date.add(2, cal.get(Calendar.DAY_OF_MONTH));

        title_string_date.add(0, String.valueOf(title_int_date.get(0)));
        if (title_int_date.get(1)<10){
            title_string_date.add(1, "0" + String.valueOf(title_int_date.get(1)));
        }else{
            title_string_date.add(1, String.valueOf(title_int_date.get(1)));
        }
        if (title_int_date.get(2)<10){
            title_string_date.add(2, "0" + String.valueOf(title_int_date.get(2)));
        }else{
            title_string_date.add(2, String.valueOf(title_int_date.get(2)));
        }

        Date.setText(title_string_date.get(0) + "년 " + title_string_date.get(1) + "월 " + title_string_date.get(2)+ "일");

        DateList.clear();
        TimeList.clear();
        NameList.clear();
        StateList.clear();

        Request_Cal2("http://miraclehwan.vps.phps.kr/SS/Request_Cal2.php", title_string_date.get(0) + title_string_date.get(1) + title_string_date.get(2));

    }

    static void cal_day(String year, String month, String day){


        Date.setText(year + "년 " + String.valueOf(Integer.parseInt(month)+1) + "월 " + day + "일");

    }

    public void Request_Cal2(final String uri, final String date){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DateList.add(jsonObject.getString("date"));
                        TimeList.add(jsonObject.getString("time"));
                        NameList.add(jsonObject.getString("name"));
                        StateList.add(jsonObject.getString("state"));
                    }
                    for (int i = 0; i < jsonArray.length(); i++){
                        switch (i){
                            case 0:
                                Time1.setText(TimeList.get(i).substring(0,2) + ":" + TimeList.get(i).substring(2,4));
                                Name1.setText(NameList.get(i));
                                State1.setText(StateList.get(i));
                                setStateColor(State1, StateList.get(i));
                                break;
                            case 1:
                                Time2.setText(TimeList.get(i).substring(0,2) + ":" + TimeList.get(i).substring(2,4));
                                Name2.setText(NameList.get(i));
                                State2.setText(StateList.get(i));
                                setStateColor(State2, StateList.get(i));
                                break;
                            case 2:
                                Time3.setText(TimeList.get(i).substring(0,2) + ":" + TimeList.get(i).substring(2,4));
                                Name3.setText(NameList.get(i));
                                State3.setText(StateList.get(i));
                                setStateColor(State3, StateList.get(i));
                                break;

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DateList.clear();
                TimeList.clear();
                NameList.clear();
                StateList.clear();
                RequestUnder_Cal2("http://miraclehwan.vps.phps.kr/SS/RequestUnder_Cal2.php", title_string_date.get(0) + title_string_date.get(1) + title_string_date.get(2));
            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");

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
        getdatajson.execute(uri, date);

    }

    public void RequestUnder_Cal2(final String uri, final String date){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        DateList.add(jsonObject.getString("date"));
                        TimeList.add(jsonObject.getString("time"));
                        NameList.add(jsonObject.getString("name"));
                        StateList.add(jsonObject.getString("state"));
                    }
                    for (int i = 0; i < jsonArray.length(); i++){
                        switch (i){
                            case 0:
                                Time_1.setText(TimeList.get(i).substring(0,2) + ":" + TimeList.get(i).substring(2,4));
                                Name_1.setText(NameList.get(i));
                                State_1.setText(StateList.get(i));
                                setStateColor(State_1, StateList.get(i));
                                break;
                            case 1:
                                Time_2.setText(TimeList.get(i).substring(0,2) + ":" + TimeList.get(i).substring(2,4));
                                Name_2.setText(NameList.get(i));
                                State_2.setText(StateList.get(i));
                                setStateColor(State_2, StateList.get(i));
                                break;

                        }
                    }

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

                    String data  = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");

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
        getdatajson.execute(uri, date);

    }

    private void setStateColor(TextView textView, String text){
        if (text.equals("완료")){
            textView.setTextColor(Color.parseColor("#00B9CE"));
        }else if (text.equals("취소")){
            textView.setTextColor(Color.RED);
        }else if (text.equals("불가")){
            textView.setTextColor(Color.RED);
        }

    }

}
