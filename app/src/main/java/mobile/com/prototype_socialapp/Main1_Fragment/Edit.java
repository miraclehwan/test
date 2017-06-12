package mobile.com.prototype_socialapp.Main1_Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-09.
 */

public class Edit extends AppCompatActivity {

    String[] LocalList = {"서울", "경기", "인천", "대구", "광주", "부산", "강원", "대전", "제주"};
    ArrayList<String[]> StadiumList = new ArrayList<>();
    Boolean LocalSelect = false;
    Boolean StadiumSelect = false;
    Boolean ImageSelect = false;

    SharedPreferences pref;


    int LocalPosition;

    final int REQ_CODE_SELECT_IMAGE=100;
    String ImagePath = null;

    ImageView BackButton;
    ImageView UserPic;
    TextView UserLocal;
    TextView UserStadium;
    TextView UserEmail;
    TextView UserPassword;
    TextView UserPasswordCheck;
    TextView JoinButton;

    String rename_pic1 = null;
    String response = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        StadiumList.add(0, new String[] {"신월 야구장", "난지 야구장", "신촌 야구장", "배명고 야구장"});
        StadiumList.add(1, new String[] {"신길 야구장", "광교 야구장", "일림 야구장", "케이티엔지 야구장"});
        StadiumList.add(2, new String[] {"청학 야구장", "송도LNG 야구장", "스카이 야구장", "인천남구 야구장"});
        StadiumList.add(3, new String[] {"두류 야구장", "시민운동장 야구장", "상원고 야구장", "대구고 야구장"});
        StadiumList.add(4, new String[] {"광주제일고 야구장", "첨단종합운동장 야구장", "동성고 야구장", "광주진흥고 야구장"});
        StadiumList.add(5, new String[] {"구덕 야구장", "삼락강변 야구장", "효민 야구장", "경성대 야구장"});
        StadiumList.add(6, new String[] {"평창 야구장", "하리 야구장", "인제 야구장", "영월덕포 야구장"});
        StadiumList.add(7, new String[] {"대전고 야구장", "갑천 야구장", "KBS 야구장", "대전초 야구장"});
        StadiumList.add(8, new String[] {"제주 야구장", "서귀포 야구장", "제주종합경기장 야구장", "제주고 야구장"});

        BackButton = (ImageView) findViewById(R.id.Edit_BackButton);
        UserPic = (ImageView) findViewById(R.id.Edit_UserPic);
        UserLocal = (TextView) findViewById(R.id.Edit_UserLocal);
        UserStadium = (TextView) findViewById(R.id.Edit_Stadium);
        UserEmail = (TextView) findViewById(R.id.Edit_Email);
        UserPassword = (TextView) findViewById(R.id.Edit_Password);
        UserPasswordCheck = (TextView) findViewById(R.id.Edit_PasswordCheck);
        JoinButton = (TextView) findViewById(R.id.Edit_JoinButton);

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UserPic.setBackground(new ShapeDrawable(new OvalShape()));
            UserPic.setClipToOutline(true);
        }

        UserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });

        UserLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit.this, android.R.layout.simple_list_item_1, LocalList);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_slect_local, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
                builder.setView(dialogView);

                ListView LocalListView = (ListView) dialogView.findViewById(R.id.Dialog_LocalListView);
                LocalListView.setAdapter(adapter);

                final AlertDialog dialog = builder.create();
                dialog.show();

                LocalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LocalPosition = position;
                        UserLocal.setText(LocalList[position]);
                        LocalSelect = true;
                        UserStadium.setText("구장을 선택해주세요.");
                        StadiumSelect = Boolean.FALSE;
                        dialog.dismiss();
                    }
                });

            }
        });

        UserStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocalSelect){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit.this, android.R.layout.simple_list_item_1, StadiumList.get(LocalPosition));

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_slect_local, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
                    builder.setView(dialogView);

                    ListView LocalListView = (ListView) dialogView.findViewById(R.id.Dialog_LocalListView);
                    LocalListView.setAdapter(adapter);

                    final AlertDialog dialog = builder.create();
                    dialog.show();

                    LocalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            UserStadium.setText(StadiumList.get(LocalPosition)[position]);
                            StadiumSelect = true;
                            dialog.dismiss();
                        }
                    });
                }else{
                    Toast.makeText(Edit.this, "지역을 먼저 선택해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        JoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ImageSelect){
                    if (LocalSelect){
                        if (StadiumSelect){
                            if (checkEmail(UserEmail.getText().toString())){
                                if (UserPassword.length()>0 && UserPassword.getText().toString().equals(UserPasswordCheck.getText().toString())){
                                    Random random = new Random();
                                    int number = random.nextInt(1000000000);
                                    Date date = new Date();
                                    SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
                                    rename_pic1 = String.valueOf(day.format(date)) + String.valueOf(number);
                                    Join(rename_pic1,
                                            UserLocal.getText().toString(),
                                            UserStadium.getText().toString(),
                                            UserEmail.getText().toString(),
                                            UserPassword.getText().toString(),
                                            "http://miraclehwan.vps.phps.kr/SS/Join.php");
                                    send_image("http://miraclehwan.vps.phps.kr/SS/image_upload.php", ImagePath);
                                }else{
                                    Toast.makeText(Edit.this, "패스워드를 확인해주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Edit.this, "이메일을 확인해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Edit.this, "구장을 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Edit.this, "지역을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Edit.this, "사진을 첨부해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    ImageSelect = true;
                    ImagePath = getPath(data.getData());
                    Log.e("ImagePath ",ImagePath);
                    Bitmap image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    ExifInterface exifInterface = null;
                    exifInterface = new ExifInterface(ImagePath);
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                    switch (orientation) {
                        case 6:
                            image_bitmap = (rotateBitmap(image_bitmap, 90));
                            break;
                        case 8:
                            image_bitmap = (rotateBitmap(image_bitmap, 270));
                            break;
                        default:
                            break;
                    }

                    UserPic.setImageBitmap(image_bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        Request_UserInfo("http://miraclehwan.vps.phps.kr/SS/Request_UserInfo.php", pref.getString("HiddenID", ""));

    }

    private String getPath(Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    private boolean checkEmail(String email){
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;
    }

    public void Request_UserInfo(final String uri, final String email){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);
                try {
                    JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        DateList.add(jsonObject.getString("date"));
//                        TimeList.add(jsonObject.getString("time"));
//                        NameList.add(jsonObject.getString("name"));
//                        StateList.add(jsonObject.getString("state"));
                        Glide.with(Edit.this).load("http://miraclehwan.vps.phps.kr/SS/video/" + jsonObject.getString("pic")).into(UserPic);
                        UserLocal.setText(jsonObject.getString("local"));
                        UserStadium.setText(jsonObject.getString("stadium"));
                        UserEmail.setText(jsonObject.getString("email"));
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

                    String data  = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");

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
        getdatajson.execute(uri, email);

    }

    public void Join(final String pic, final String local, final String stadium, final String email, final String password, final String uri){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.e("Join Message ", s);
                if (Boolean.valueOf(s)){
                    Toast.makeText(Edit.this, "가입완료", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(Edit.this, "중복된 이메일이 존재합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                BufferedReader bufferedReader = null;
                try{

                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String data  = URLEncoder.encode("pic", "UTF-8") + "=" + URLEncoder.encode(pic, "UTF-8");
                    data += "&" + URLEncoder.encode("local", "UTF-8") + "=" + URLEncoder.encode(local, "UTF-8");
                    data += "&" + URLEncoder.encode("stadium", "UTF-8") + "=" + URLEncoder.encode(stadium, "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
        getdatajson.execute(pic, local, stadium, email, password, uri);

    }


    /*
    Send to Image
     */
    public void send_image(String uri, String pic1){


        class GetImageData extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                String pic1 = params[1];

                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                        .setCharset(Charset.forName("UTF-8"))
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

                multipartEntityBuilder.setBoundary("----");

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                ExifInterface exifInterface = null;
                Bitmap re_pic1= null;
                if(pic1.length()> 2){
                    Bitmap src_pic1 = BitmapFactory.decodeFile(pic1, options);
                    re_pic1 = Bitmap.createScaledBitmap(src_pic1, src_pic1.getWidth(), src_pic1.getHeight(), true);


                    try {
                        exifInterface = new ExifInterface(pic1);
                        int pic1_orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                        Log.e("pic_orientation : ", String.valueOf(pic1_orientation));
                        switch (pic1_orientation) {
                            case 6:
                                re_pic1 = (rotateBitmap(re_pic1, 90));
                                break;
                            case 8:
                                re_pic1 = (rotateBitmap(re_pic1, 270));
                                break;
                            default:
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                byte[] data1 = null;
                if(re_pic1!=null){
                    re_pic1.compress(Bitmap.CompressFormat.JPEG, 100, bos1);
                    data1 = bos1.toByteArray();
                }


                if(re_pic1!=null){
                    multipartEntityBuilder.addPart("pic1", new ByteArrayBody(data1, "image/jpeg"));
                }

                multipartEntityBuilder.addTextBody("pic1name", rename_pic1);


                HttpEntity entity = multipartEntityBuilder.build();

                HttpClient client = AndroidHttpClient.newInstance("Android");

                HttpPost post = new HttpPost(uri);

                post.setEntity(entity);
                Log.e("Response Log : ", "1");

                try {

                    HttpResponse httpRes;
                    httpRes = client.execute(post);
                    HttpEntity httpEntity = httpRes.getEntity();
                    Log.e("Response Log : ", "2");
                    if (httpEntity != null) {
                        response = EntityUtils.toString(httpEntity);
                        Log.e("Response Log : ", response);
                    }
                    Log.e("Response Log : ", "3");
                } catch (IOException e) {
                    e.printStackTrace();
                }



                return null;
            }
        }
        GetImageData getImageData = new GetImageData();
        getImageData.execute(uri, pic1);
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int degree){
        if (bitmap != null && degree != 0){
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, (float)bitmap.getWidth()/2, (float)bitmap.getHeight()/2);
            Bitmap tempbitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);{
                if (bitmap != tempbitmap){
                    bitmap.recycle();
                    bitmap = tempbitmap;
                }
            }
        }
        return bitmap;
    }


}
