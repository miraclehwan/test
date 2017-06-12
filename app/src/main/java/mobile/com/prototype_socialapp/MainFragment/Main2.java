package mobile.com.prototype_socialapp.MainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import mobile.com.prototype_socialapp.Main2_Fragment.To1;
import mobile.com.prototype_socialapp.R;

/**
 * Created by Daehwan Kim on 2017-06-09.
 * 메인메뉴2 Fragment
 */

public class Main2 extends Fragment{

    To1 to1;

    public static FragmentManager fm;

    public Main2(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_main2, container, false);

        to1 = To1.newInstance();

        fm = getFragmentManager();

        fm.beginTransaction().replace(R.id.Main2_Fragement, to1).commit();


        return layout;
    }
}
