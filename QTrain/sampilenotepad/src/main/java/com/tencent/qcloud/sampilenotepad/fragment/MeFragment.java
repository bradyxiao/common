package com.tencent.qcloud.sampilenotepad.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.sampilenotepad.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    ImageView photoView;
    TextView textMe, textSystem, textAbort, textSetting;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the date_picker_dialog for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        photoView = view.findViewById(R.id.image_me);
        textMe = view.findViewById(R.id.text_me);
        textSystem = view.findViewById(R.id.text_system);
        textAbort = view.findViewById(R.id.text_abort);
        textSetting = view.findViewById(R.id.text_setting);
        String htmlSource = "昵称<br/>ID";
        textMe.setText(Html.fromHtml(htmlSource));
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
            case R.id.image_me:
                break;
            case R.id.text_me:
                break;
            case R.id.text_system:
                break;
            case R.id.text_abort:
                break;
            case R.id.text_setting:
                break;
            default:
                break;
        }
    }

    private void onClickImageView(){

    }

    private void onClickTextSystem(){}

    private void onClickTextMe(){}

    private void onClickTextAbort(){}

    private void onClickTextSetting(){}
}
