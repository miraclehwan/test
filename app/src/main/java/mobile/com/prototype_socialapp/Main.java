package mobile.com.prototype_socialapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

import mobile.com.prototype_socialapp.MainFragment.Main1;
import mobile.com.prototype_socialapp.MainFragment.Main2;
import mobile.com.prototype_socialapp.MainFragment.Main3;
import mobile.com.prototype_socialapp.MainFragment.Main4;

/**
 * Created by Daehwan Kim on 2017-06-09.
 */

public class Main extends AppCompatActivity {

    ViewPager mViewPager;
    ImageView Menu1;
    ImageView Menu2;
    ImageView Menu3;
    ImageView Menu4;
    ArrayList<ImageView> MenuList = new ArrayList<>();
    int Icon[] = {R.drawable.calendar, R.drawable.trophy, R.drawable.videoplayer , R.drawable.internet};
    int setIcon[] = {R.drawable.ccalendar, R.drawable.ctrophy, R.drawable.cvideoplayer , R.drawable.cinternet};
    int CurrentPosition = 0;
    int NextPosition = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        {
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        }
        getWindow().setAttributes(attrs);
        setContentView(R.layout.activity_main);




        mViewPager = (ViewPager) findViewById(R.id.Main_ViewPager);
        Menu1 = (ImageView) findViewById(R.id.Main_Menu1);
        Menu2 = (ImageView) findViewById(R.id.Main_Menu2);
        Menu3 = (ImageView) findViewById(R.id.Main_Menu3);
        Menu4 = (ImageView) findViewById(R.id.Main_Menu4);

        mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);

        Menu1.setOnClickListener(movePageListener);
        Menu1.setTag(0);
        Menu2.setOnClickListener(movePageListener);
        Menu2.setTag(1);
        Menu3.setOnClickListener(movePageListener);
        Menu3.setTag(2);
        Menu4.setOnClickListener(movePageListener);
        Menu4.setTag(3);

        MenuList.add(0, Menu1);
        MenuList.add(1, Menu2);
        MenuList.add(2, Menu3);
        MenuList.add(3, Menu4);

        mViewPager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        Menu1.setImageResource(setIcon[0]);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setIconColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            NextPosition = tag;
            setIconColor(tag);
            mViewPager.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Main1();
                case 1:
                    return new Main2();
                case 2:
                    return new Main3();
                case 3:
                    return new Main4();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    private void setIconColor(int number){
            for (int i=0; i<4; i++){
                MenuList.get(i).setImageResource(Icon[i]);
            }
            MenuList.get(number).setImageResource(setIcon[number]);
    }

}
