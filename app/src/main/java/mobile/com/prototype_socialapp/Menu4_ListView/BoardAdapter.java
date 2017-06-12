package mobile.com.prototype_socialapp.Menu4_ListView;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class BoardAdapter extends BaseAdapter {

    ArrayList<BoardData> datas;
    LayoutInflater inflater;

    public BoardAdapter(LayoutInflater inflater, ArrayList<BoardData> datas){
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
            convertView = inflater.inflate(R.layout.listview_item_board, null);
        }

        TextView Date = (TextView) convertView.findViewById(R.id.BoardItem_Date);
        TextView Title  = (TextView) convertView.findViewById(R.id.textView12);
        TextView Reply = (TextView) convertView.findViewById(R.id.BoardItem_Reply);
        ImageView Image = (ImageView) convertView.findViewById(R.id.BoardItem_Image);

        Log.e("Adapter LOG", datas.get(position).getImageurl());

        if (datas.get(position).getTitle().length() > 10){
            Title.setText(datas.get(position).getTitle().substring(0,10) + "...");
        }else{
            Log.e("Adapter LOG", datas.get(position).getTitle());
            Title.setText(datas.get(position).getTitle());
        }

        Reply.setText(datas.get(position).getReply());

        Glide.with(convertView.getContext()).load("http://miraclehwan.vps.phps.kr/SS/pic/" + datas.get(position).getImageurl()).into(Image);

        Date.setText(datas.get(position).getUser() + " | " +
                datas.get(position).getDay().substring(0,4) +"." + datas.get(position).getDay().substring(4,6) + "."+ datas.get(position).getDay().substring(6,8) + " | " +
                "조회 " + datas.get(position).getCount()
        );

        return convertView;
    }
}

