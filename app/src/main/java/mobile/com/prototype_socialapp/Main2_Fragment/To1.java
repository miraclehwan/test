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

/**
 * Created by Daehwan Kim on 2017-06-11.
 */

public class To1 extends Fragment {

    View view;

    To2 to2;

    public static To1 newInstance(){
        To1 to1 = new To1();
        return to1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toner1, container, false);

        to2 = To2.newInstance();

        TextView StartButton = (TextView) view.findViewById(R.id.To1_StartButton);


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2.fm.beginTransaction().replace(R.id.Main2_Fragement, to2).commit();
            }
        });

        return view;
    }
}
