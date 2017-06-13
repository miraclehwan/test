package mobile.com.prototype_socialapp.MainFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sch.calendar.CalendarView;
import com.sch.calendar.adapter.VagueAdapter;
import com.sch.calendar.annotation.DayOfMonth;
import com.sch.calendar.annotation.Month;
import com.sch.calendar.entity.Date;
import com.sch.calendar.listener.OnDateClickedListener;
import com.sch.calendar.listener.OnMonthChangedListener;
import com.sch.calendar.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobile.com.prototype_socialapp.Checkin;
import mobile.com.prototype_socialapp.Join;
import mobile.com.prototype_socialapp.Main1_Fragment.Edit;
import mobile.com.prototype_socialapp.Main1_Fragment.Main1_Cal1;
import mobile.com.prototype_socialapp.Main1_Fragment.Main1_Cal2;
import mobile.com.prototype_socialapp.Main2_Fragment.To1;
import mobile.com.prototype_socialapp.R;

import static mobile.com.prototype_socialapp.MainFragment.Main2.fm;

/**
 * Created by Daehwan Kim on 2017-06-09.
 *
 * 메인메뉴1 Fragment
 */

public class Main1 extends Fragment{

    ArrayList<String> WeatherList = new ArrayList<>();
    ArrayList<String> TimeList = new ArrayList<>();
    ArrayList<Double> KelvinList = new ArrayList<>();

    ArrayList<TextView> WeatherTimeList = new ArrayList<>();
    ArrayList<TextView> WeatherKelvinList = new ArrayList<>();
    ArrayList<ImageView> WeatherImageList = new ArrayList<>();
    ArrayList<LinearLayout> BackBgColorList = new ArrayList<>();

    TextView WeatherTime1;
    TextView WeatherTime2;
    TextView WeatherTime3;
    TextView WeatherTime4;
    TextView WeatherTime5;
    TextView WeatherTime6;
    TextView WeatherTime7;

    ImageView WeatherImage1;
    ImageView WeatherImage2;
    ImageView WeatherImage3;
    ImageView WeatherImage4;
    ImageView WeatherImage5;
    ImageView WeatherImage6;
    ImageView WeatherImage7;

    TextView WeatherKelvin1;
    TextView WeatherKelvin2;
    TextView WeatherKelvin3;
    TextView WeatherKelvin4;
    TextView WeatherKelvin5;
    TextView WeatherKelvin6;
    TextView WeatherKelvin7;

    Main1_Cal2 main1_cal2;
    Main1_Cal1 main1_cal1;
    public static FragmentManager fm;
    public static ImageView BackButton;


    public Main1(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main1, container, false);

        TextView LocalDate = (TextView) layout.findViewById(R.id.LocalDate);

        WeatherTime1 = (TextView) layout.findViewById(R.id.WeatherTime1);
        WeatherTime2 = (TextView) layout.findViewById(R.id.WeatherTime2);
        WeatherTime3 = (TextView) layout.findViewById(R.id.WeatherTime3);
        WeatherTime4 = (TextView) layout.findViewById(R.id.WeatherTime4);
        WeatherTime5 = (TextView) layout.findViewById(R.id.WeatherTime5);
        WeatherTime6 = (TextView) layout.findViewById(R.id.WeatherTime6);
        WeatherTime7 = (TextView) layout.findViewById(R.id.WeatherTime7);

        WeatherImage1 = (ImageView) layout.findViewById(R.id.WeatherImage1);
        WeatherImage2 = (ImageView) layout.findViewById(R.id.WeatherImage2);
        WeatherImage3 = (ImageView) layout.findViewById(R.id.WeatherImage3);
        WeatherImage4 = (ImageView) layout.findViewById(R.id.WeatherImage4);
        WeatherImage5 = (ImageView) layout.findViewById(R.id.WeatherImage5);
        WeatherImage6 = (ImageView) layout.findViewById(R.id.WeatherImage6);
        WeatherImage7 = (ImageView) layout.findViewById(R.id.WeatherImage7);

        WeatherKelvin1 = (TextView) layout.findViewById(R.id.WeatherKelvin1);
        WeatherKelvin2 = (TextView) layout.findViewById(R.id.WeatherKelvin2);
        WeatherKelvin3 = (TextView) layout.findViewById(R.id.WeatherKelvin3);
        WeatherKelvin4 = (TextView) layout.findViewById(R.id.WeatherKelvin4);
        WeatherKelvin5 = (TextView) layout.findViewById(R.id.WeatherKelvin5);
        WeatherKelvin6 = (TextView) layout.findViewById(R.id.WeatherKelvin6);
        WeatherKelvin7 = (TextView) layout.findViewById(R.id.WeatherKelvin7);

        LinearLayout BackBgColor1 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor1);
        LinearLayout BackBgColor2 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor2);
        LinearLayout BackBgColor3 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor3);
        LinearLayout BackBgColor4 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor4);
        LinearLayout BackBgColor5 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor5);
        LinearLayout BackBgColor6 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor6);
        LinearLayout BackBgColor7 = (LinearLayout) layout.findViewById(R.id.Main1_BgColor7);

        BackBgColorList.add(BackBgColor1);
        BackBgColorList.add(BackBgColor2);
        BackBgColorList.add(BackBgColor3);
        BackBgColorList.add(BackBgColor4);
        BackBgColorList.add(BackBgColor5);
        BackBgColorList.add(BackBgColor6);
        BackBgColorList.add(BackBgColor7);

        WeatherTimeList.add(WeatherTime1);
        WeatherTimeList.add(WeatherTime2);
        WeatherTimeList.add(WeatherTime3);
        WeatherTimeList.add(WeatherTime4);
        WeatherTimeList.add(WeatherTime5);
        WeatherTimeList.add(WeatherTime6);
        WeatherTimeList.add(WeatherTime7);

        WeatherKelvinList.add(WeatherKelvin1);
        WeatherKelvinList.add(WeatherKelvin2);
        WeatherKelvinList.add(WeatherKelvin3);
        WeatherKelvinList.add(WeatherKelvin4);
        WeatherKelvinList.add(WeatherKelvin5);
        WeatherKelvinList.add(WeatherKelvin6);
        WeatherKelvinList.add(WeatherKelvin7);

        WeatherImageList.add(WeatherImage1);
        WeatherImageList.add(WeatherImage2);
        WeatherImageList.add(WeatherImage3);
        WeatherImageList.add(WeatherImage4);
        WeatherImageList.add(WeatherImage5);
        WeatherImageList.add(WeatherImage6);
        WeatherImageList.add(WeatherImage7);

BackButton = (ImageView) layout.findViewById(R.id.Main1_BackButton);
        BackButton.setVisibility(View.INVISIBLE);

        java.util.Date date = new java.util.Date();
        SimpleDateFormat day = new SimpleDateFormat("yy"+"."+"MM"+"."+"dd");
        LocalDate.setText("경기도 수원시 현재 날씨 ("+day.format(date) + ")");

        LocalWeather("seoul");

        main1_cal2 = Main1_Cal2.newInstance();
        main1_cal1 = Main1_Cal1.newInstance();

        fm = getFragmentManager();

        fm.beginTransaction().replace(R.id.Main1_Fragement, main1_cal1).commit();

//        TextView EditButton = (TextView) layout.findViewById(R.id.Main1_Edit);
//        EditButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Edit.class);
//                startActivity(intent);
//            }
//        });


        return layout;
    }

    public void LocalWeather(final String local){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray list = jsonObject.getJSONArray("list");
                    for (int i=0; i<list.length(); i++){
                        JSONObject item = list.getJSONObject(i);

                        String tempTime = item.getString("dt_txt").substring(11, 13);
                        TimeList.add(tempTime);
                        WeatherList.add(item.getJSONArray("weather").getJSONObject(0).getString("main"));
                        KelvinList.add(Double.parseDouble(item.getJSONObject("main").getString("temp")));
                    }

                    java.util.Date date = new java.util.Date();
                    SimpleDateFormat day = new SimpleDateFormat("HH");
                    String S_Day = day.format(date);
                    String selectLayout = null;


                    if (S_Day.equals("00") || S_Day.equals("01") || S_Day.equals("02")){
                        selectLayout = "00";
                    }else if (S_Day.equals("03") || S_Day.equals("04") || S_Day.equals("05")){
                        selectLayout = "03";
                    }else if (S_Day.equals("06") || S_Day.equals("07") || S_Day.equals("08")){
                        selectLayout = "06";
                    }else if (S_Day.equals("09") || S_Day.equals("10") || S_Day.equals("11")){
                        selectLayout = "09";
                    }else if (S_Day.equals("12") || S_Day.equals("13") || S_Day.equals("14")){
                        selectLayout = "12";
                    }else if (S_Day.equals("15") || S_Day.equals("16") || S_Day.equals("17")){
                        selectLayout = "15";
                    }else if (S_Day.equals("18") || S_Day.equals("19") || S_Day.equals("20")){
                        selectLayout = "18";
                    }else if (S_Day.equals("21") || S_Day.equals("22") || S_Day.equals("23")) {
                        selectLayout = "21";
                    }


                    for (int i=0; i<7; i++){
                        if (WeatherList.get(i).equals("Clear")){
                            WeatherImageList.get(i).setImageResource(R.drawable.sun);
                        }else if (WeatherList.get(i).equals("Rain")){
                            WeatherImageList.get(i).setImageResource(R.drawable.rain);
                        }else if (WeatherList.get(i).equals("Clouds")){
                            WeatherImageList.get(i).setImageResource(R.drawable.cloudy);
                        }else{
                            WeatherImageList.get(i).setImageResource(R.drawable.suncloud);
                        }

                        if (TimeList.get(i).equals(selectLayout)){
                            BackBgColorList.get(i).setBackgroundColor(Color.GRAY);
                        }

                        WeatherTimeList.get(i).setText(TimeList.get(i)+"시");
                        WeatherKelvinList.get(i).setText(String.valueOf(Math.round(KelvinList.get(i)-273.15))+"℃");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?q="+local+",kr&mode=json&json&APPID=41d585977693d0813559fe0bc37bb9fd&cnt=7");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("local", "UTF-8") + "=" + URLEncoder.encode(local, "UTF-8");

                    Log.e("Join Message ", data);

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
        getdatajson.execute(local);

    }

}
