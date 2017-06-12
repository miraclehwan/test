package mobile.com.prototype_socialapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Daehwan Kim on 2017-06-09.
 */

public class Login extends AppCompatActivity {

    EditText UserID;
    EditText UserPASSWORD;
    CheckBox AutoCheckBox;
    TextView JoinButton;
    TextView LoginButton;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("pref", MODE_PRIVATE);
        editor = pref.edit();

        UserID = (EditText) findViewById(R.id.Login_UserID);
        UserPASSWORD = (EditText) findViewById(R.id.Login_UserPASSWORD);
        AutoCheckBox = (CheckBox) findViewById(R.id.Login_AutoCheckBox);
        JoinButton = (TextView) findViewById(R.id.Login_JoinButton);
        LoginButton = (TextView) findViewById(R.id.Login_LoginButton);

        if (pref.getBoolean("AutoLoginCheck", false)){
            AutoCheckBox.setChecked(true);
            UserID.setText(pref.getString("UserID", ""));
            UserPASSWORD.setText(pref.getString("UserPASSWORD", ""));
            Login(UserID.getText().toString(),
                    UserPASSWORD.getText().toString(),
                    "http://miraclehwan.vps.phps.kr/SS/Login.php");
        }

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserID.length()>0 && UserPASSWORD.length()>0){
                    Login(UserID.getText().toString(),
                            UserPASSWORD.getText().toString(),
                            "http://miraclehwan.vps.phps.kr/SS/Login.php");
                }
            }
        });

        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MoveJoinActivity = new Intent(Login.this, Join.class);
                startActivity(MoveJoinActivity);
            }
        });

        AutoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    editor.putString("UserID", "");
                    editor.putString("UserPASSWORD", "");
                    editor.putBoolean("AutoLoginCheck", false);
                    editor.commit();
                }
            }
        });

    }

    public void Login(final String email, final String password, final String uri){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);
                if (Boolean.valueOf(s)){
                    if (AutoCheckBox.isChecked()){
                        editor.putString("UserID", UserID.getText().toString());
                        editor.putString("UserPASSWORD", UserPASSWORD.getText().toString());
                        editor.putBoolean("AutoLoginCheck", true);
                        editor.commit();
                    }else{
                        editor.putString("UserID", "");
                        editor.putString("UserPASSWORD", "");
                        editor.putBoolean("AutoLoginCheck", false);
                        editor.commit();
                    }
                    editor.putString("HiddenID", UserID.getText().toString());
                    editor.commit();
                    Intent MoveLoginActivity = new Intent(Login.this, Main.class);
                    startActivity(MoveLoginActivity);
                    finish();
                }else{
                    Toast.makeText(Login.this, "아이디, 패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
        getdatajson.execute(email, password, uri);

    }

}
