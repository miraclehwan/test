package mobile.com.prototype_socialapp.Main1_Fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import mobile.com.prototype_socialapp.Main2_Fragment.To2;
import mobile.com.prototype_socialapp.MainFragment.Main1;
import mobile.com.prototype_socialapp.MainFragment.Main2;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class Main1_Cal1 extends Fragment {

    static String aa;
    static String bb;
    static String cc;
    View view;
    Main1_Cal2 main1_cal2;
    static CalendarView cv;
    static HashSet<Date> events;
    ArrayList<String> DateList = new ArrayList<>();
    Boolean a = true;
    public static Main1_Cal1 newInstance(){
        Main1_Cal1 to1 = new Main1_Cal1();
        return to1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cal1, container, false);
        main1_cal2 = Main1_Cal2.newInstance();
        Date date = new Date();
        SimpleDateFormat day = new SimpleDateFormat("yyyyMM");
        Request_Cal2("http://miraclehwan.vps.phps.kr/SS/Request_Calendar.php", String.valueOf(day.format(date)));



        events = new HashSet<>();

        cv = ((CalendarView) view.findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {

            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();

            }

            @Override
            public void onDayPress(Date date) {
                // show returned day
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String a= df.format(date);
                Log.e("DaehwanLog", a);
                Main1.fm.beginTransaction().replace(R.id.Main1_Fragement, main1_cal2).commit();

aa = a.substring(0,4);
bb = a.substring(4,6);
cc = a.substring(6,8);

            }
        });

        return view;
    }

    static void reRenderingCalendar(){
        cv.updateCalendar(events);
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
                    }

                    for (int i=0; i<DateList.size(); i++){
//                        String year = DateList.get(i).substring(0,4);
//                        String month = DateList.get(i).substring(4,6);
//                        String month = DateList.get(i).substring(4,6);


                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                            Date temp_date = simpleDateFormat.parse(DateList.get(i));
                            events.add(temp_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                    cv.updateCalendar(events);
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

}
