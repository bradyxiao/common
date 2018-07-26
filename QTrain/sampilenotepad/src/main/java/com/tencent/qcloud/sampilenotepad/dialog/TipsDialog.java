package com.tencent.qcloud.sampilenotepad.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import com.tencent.qcloud.commonutils.log.LogUtils;
import com.tencent.qcloud.sampilenotepad.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.app.Activity.RESULT_OK;


/**
 * Created by bradyxiao on 2018/7/26.
 *
 * @author bradyxiao
 * @Time 2018/7/26
 */

public class TipsDialog extends DialogFragment {

    public static final String EXTRA_DATE = "com.tencent.qcloud.dialog.date";

    public static TipsDialog newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        TipsDialog instance = new TipsDialog();
        instance.setArguments(args);
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.date_picker_dialog, null);
        final DatePicker datePicker = dialogView.findViewById(R.id.dialog_data_picker_id);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Date selectDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                getArguments().putSerializable(EXTRA_DATE, selectDate);
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle("时间")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.d("XIAO", "" + datePicker.getDayOfMonth());
                        setResult((Date) getArguments().getSerializable(EXTRA_DATE), RESULT_OK);
                    }
                })
                .create();
    }

    private void setResult(Date date, int resultCode){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
