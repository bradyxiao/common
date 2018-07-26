package com.tencent.qcloud.sampilenotepad.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.qcloud.commonutils.Utils;
import com.tencent.qcloud.commonutils.log.LogUtils;
import com.tencent.qcloud.sampilenotepad.EditActivity;
import com.tencent.qcloud.sampilenotepad.R;
import com.tencent.qcloud.sampilenotepad.dialog.TipsDialog;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.tencent.qcloud.sampilenotepad.dialog.TipsDialog.EXTRA_DATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters

    LinearLayout gridHome, gridList, gridSetting;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the date_picker_dialog for this fragment
        /**
         * container: 父视图；
         * attachToRoot: 是否添加到父视图, false: 在代码中添加给父视图， true：通过布局来添加
         *
         */
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridHome = view.findViewById(R.id.grid_home);
        gridList = view.findViewById(R.id.grid_list);
        gridSetting = view.findViewById(R.id.grid_setting);
        gridHome.setOnClickListener(this);
        gridList.setOnClickListener(this);
        gridSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.grid_home:
                onGridHome();
                break;
            case R.id.grid_list:
                onGridList();
                break;
            case R.id.grid_setting:
                break;
        }
    }

    private void onGridHome(){
        Intent intent = new Intent(this.getContext(), EditActivity.class);
        startActivity(intent);
    }

    private void onGridList(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        TipsDialog tipsDialog = TipsDialog.newInstance(new Date(System.currentTimeMillis() - 2 * 24 * 3600 * 1000));
        tipsDialog.setTargetFragment(HomeFragment.this, 100);
        tipsDialog.show(fragmentManager, "date_picker_tag");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            Date date = (Date) data.getSerializableExtra(EXTRA_DATE);
            Toast.makeText(getActivity(), date.toString(), Toast.LENGTH_SHORT).show();
            LogUtils.d("XIAO", date.toString());
        }
    }
}
