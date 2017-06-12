package mobile.com.prototype_socialapp.Comment_ListView;

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

import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class CommentAdapter extends BaseAdapter {

    ArrayList<CommentData> datas;
    LayoutInflater inflater;

    public CommentAdapter(LayoutInflater inflater, ArrayList<CommentData> datas){
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
            convertView = inflater.inflate(R.layout.listview_item_comment, null);
        }

        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        ImageView Image = (ImageView) convertView.findViewById(R.id.CommentItem_Image);
        TextView User = (TextView) convertView.findViewById(R.id.CommentItem_User);
        TextView Comment = (TextView) convertView.findViewById(R.id.CommentItem_Comment);
        TextView Date = (TextView) convertView.findViewById(R.id.CommentItem_Date);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Image.setBackground(new ShapeDrawable(new OvalShape()));
            Image.setClipToOutline(true);
        }

        Glide.with(convertView.getContext()).load("http://miraclehwan.vps.phps.kr/SS/pic/" + datas.get(position).getImageurl()).into(Image);
        User.setText(datas.get(position).getUser());
        Comment.setText(datas.get(position).getComment());
        Date.setText(
                datas.get(position).getDate().substring(0,4) +"." +
                        datas.get(position).getDate().substring(4,6) + "."+
                        datas.get(position).getDate().substring(6,8) + " " +
                        datas.get(position).getDate().substring(8,10) + ":" +
                        datas.get(position).getDate().substring(10,12));

        return convertView;
    }
}
