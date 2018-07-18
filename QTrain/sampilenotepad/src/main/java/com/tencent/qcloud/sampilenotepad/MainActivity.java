package com.tencent.qcloud.sampilenotepad;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.qcloud.sampilenotepad.fragment.HomeFragment;
import com.tencent.qcloud.sampilenotepad.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MeFragment.OnFragmentInteractionListener{

    private LinearLayout homeTab, listTab, meTab;
    private ImageButton homeImg, listImg, meImg;
    private TextView homeText, listText, meText;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private ViewPagerAdapter viewPagerAdapter;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private final int ADVERTISE_MSG_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //全屏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //取消状态栏
        setContentView(R.layout.activity_main);
        initUI();
        initViewPager();
        initEvent();
        detectAdvertisement();
    }

    private void initUI(){
        viewPager = findViewById(R.id.id_viewpage);
        homeTab = findViewById(R.id.id_tab_home);
        listTab = findViewById(R.id.id_tab_list);
        meTab = findViewById(R.id.id_tab_settings);
        homeImg = findViewById(R.id.id_tab_home_img);
        listImg = findViewById(R.id.id_tab_list_img);
        meImg = findViewById(R.id.id_tab_settings_img);
        homeText = findViewById(R.id.id_tab_home_text);
        listText = findViewById(R.id.id_tab_list_text);
        meText = findViewById(R.id.id_tab_settings_text);
    }

    private void initViewPager(){
        fragmentList = new ArrayList<>();
        fragmentList.add( new HomeFragment());
        fragmentList.add( new MeFragment());
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initEvent(){
        homeTab.setOnClickListener(this);
        listTab.setOnClickListener(this);
        meTab.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImg();
                        homeImg.setImageResource(R.drawable.ic_home_press_24dp);
                        homeText.setTextColor(getResources().getColor(R.color.teat_100));
                        break;
                    case 1:
                        resetImg();
                        listImg.setImageResource(R.drawable.ic_view_list_press_24dp);
                        listText.setTextColor(getResources().getColor(R.color.teat_100));
                        break;
                    case 2:
                        resetImg();
                        meImg.setImageResource(R.drawable.ic_person_press_24dp);
                        meText.setTextColor(getResources().getColor(R.color.teat_100));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }

    private void detectAdvertisement(){
        new Thread(new Runnable() {
            @Override
            public void run() {
               mainHandler.post(new Runnable() {
                   @Override
                   public void run() {
                       Intent intent = new Intent(MainActivity.this, AdvertiseActivity.class);
                       intent.putExtra("MESSAGE", "this is a advertisement");
                       startActivityForResult(intent, ADVERTISE_MSG_REQUEST_CODE);
                   }
               });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tab_home:
                viewPager.setCurrentItem(0);
                resetImg();
                homeImg.setImageResource(R.drawable.ic_home_press_24dp);
                homeText.setTextColor(getResources().getColor(R.color.teat_100));
                break;
            case R.id.id_tab_list:
                viewPager.setCurrentItem(1);
                resetImg();
                listImg.setImageResource(R.drawable.ic_view_list_press_24dp);
                listText.setTextColor(getResources().getColor(R.color.teat_100));
                break;
            case R.id.id_tab_settings:
                viewPager.setCurrentItem(2);
                resetImg();
                meImg.setImageResource(R.drawable.ic_person_press_24dp);
                meText.setTextColor(getResources().getColor(R.color.teat_100));
                break;
            default:
                break;
        }

    }

    /**
     * 把所有图片变暗
     */
    private void resetImg() {
        homeImg.setImageResource(R.drawable.ic_home_black_24dp);
        listImg.setImageResource(R.drawable.ic_view_list_black_24dp);
        meImg.setImageResource(R.drawable.ic_person_black_24dp);
        homeText.setTextColor(Color.WHITE);
        listText.setTextColor(Color.WHITE);
        meText.setTextColor(Color.WHITE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        List<Fragment> fragmentList;
        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}


