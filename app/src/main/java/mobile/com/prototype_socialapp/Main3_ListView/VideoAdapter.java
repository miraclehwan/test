package mobile.com.prototype_socialapp.Main3_ListView;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobile.com.prototype_socialapp.Comment_ListView.CommentData;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class VideoAdapter extends BaseAdapter {

    ArrayList<VideoData> datas;
    LayoutInflater inflater;

    public VideoAdapter(LayoutInflater inflater, ArrayList<VideoData> datas){
        this.inflater = inflater;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = inflater.inflate(R.layout.listview_item_video, null);
        }

        ImageView Image = (ImageView) convertView.findViewById(R.id.Videoitem_Image);
        TextView Title = (TextView) convertView.findViewById(R.id.Videoitem_Title);
        TextView Date = (TextView) convertView.findViewById(R.id.Videoitem_Date);

        Glide.with(convertView.getContext()).load("http://miraclehwan.vps.phps.kr/SS/video/" + datas.get(position).getImageurl()).into(Image);
        Title.setText(datas.get(position).getTitle());
        int idx = datas.get(position).getUser().indexOf("@");
        Date.setText(datas.get(position).getUser().substring(0, idx) + " | " +
                datas.get(position).getDate().substring(0,4) +"." + datas.get(position).getDate().substring(4,6) + "."+ datas.get(position).getDate().substring(6,8) + " | " +
                "조회 " + datas.get(position).getCount()
        );

        return convertView;
    }
}
