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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    View view;
    Main1_Cal2 main1_cal2;
    CustomCalendarView calendarView;
    Calendar currentCalendar;
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

//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialize CustomCalendarView from layout
        calendarView = (CustomCalendarView) view.findViewById(R.id.calendar_view);



        //Initialize calendar with date
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);

        //Show/hide overflow days of a month
        calendarView.setShowOverflowDate(false);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);



        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                Main1.fm.beginTransaction().replace(R.id.Main1_Fragement, main1_cal2).commit();
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
                Request_Cal2("http://miraclehwan.vps.phps.kr/SS/Request_Calendar.php", df.format(date));
            }
        });

        //Setting custom font
//        final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Arch_Rival_Bold.ttf");
//        if (null != typeface) {
//            calendarView.setCustomTypeface(typeface);
//            calendarView.refreshCalendar(currentCalendar);
//        }



        return view;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // handle arrow click here
//        if (item.getItemId() == android.R.id.home) {
//            finish(); // close this activity and return to preview activity (if there is any)
//        }
//        return super.onOptionsItemSelected(item);
//    }
    private class DisabledColorDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {


            int tempyear = dayView.getDate().getYear()+1900;
            int tempmonth = dayView.getDate().getMonth()+1;
            int tempday = dayView.getDate().getDate();
            String S_month = null;
            String S_day = null;

            if (tempmonth<10){
                S_month = "0"+String.valueOf(tempmonth);
            }else{
                S_month = String.valueOf(tempmonth);
            }
            if (tempday<10){
                S_day = "0"+String.valueOf(tempday);
            }else{
                S_day = String.valueOf(tempday);
            }
            String viewdate = String.valueOf(tempyear) + S_month + S_day;
            Log.e("Log", viewdate);

            for (int i=0; i<DateList.size(); i++){
                if (DateList.get(i).equals(viewdate)){
                    int color = Color.parseColor("#FF0000");
                    dayView.setBackgroundResource(R.drawable.selected_date_shape);
                    ((GradientDrawable)dayView.getBackground()).setColor(Color.RED);
                }
            }

        }
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (a){
                    List decorators = new ArrayList<>();
                    decorators.add(new DisabledColorDecorator());
                    calendarView.setDecorators(decorators);
                    calendarView.refreshCalendar(currentCalendar);
                    a=false;
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

    private void CheckCa(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 5, 10);
//        calendar.set(Calendar.YEAR, 2017);
//        calendar.set(Calendar.MONTH, 5);
//        calendar.set(Calendar.DAY_OF_MONTH, 10);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }



}
