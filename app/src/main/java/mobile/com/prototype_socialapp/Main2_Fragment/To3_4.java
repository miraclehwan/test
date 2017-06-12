package mobile.com.prototype_socialapp.Main2_Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mobile.com.prototype_socialapp.MainFragment.Main2;
import mobile.com.prototype_socialapp.R;

import static mobile.com.prototype_socialapp.Main2_Fragment.To2.JoinPrice;
import static mobile.com.prototype_socialapp.Main2_Fragment.To2.Win1Prize;
import static mobile.com.prototype_socialapp.Main2_Fragment.To2.Win2Prize;
import static mobile.com.prototype_socialapp.Main2_Fragment.To2.toNumFormat;

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class To3_4 extends Fragment {

    View view;
    public static TextView Title;

    public static To3_4 newInstance(){
        To3_4 to1 = new To3_4();
        return to1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toner3_to4, container, false);

        Title = (TextView) view.findViewById(R.id.To4_Title);
        Title.setText("최종우승 " + toNumFormat(Integer.parseInt(Win1Prize.getText().toString())) + " | " +
                "준우승 " + toNumFormat(Integer.parseInt(Win2Prize.getText().toString())) + " | " +
                "참가비 " + toNumFormat(Integer.parseInt(JoinPrice.getText().toString())));

        return view;
    }
}
