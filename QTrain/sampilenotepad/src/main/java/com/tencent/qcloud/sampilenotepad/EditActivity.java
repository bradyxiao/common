package com.tencent.qcloud.sampilenotepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.qcloud.commonutils.Utils;
import com.tencent.qcloud.commonutils.log.LogUtils;

import java.io.File;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTitle, editContent;
    TextView descText;
    ImageButton saveBtn, clearBtn, deleteBtn;

    View saveDialogView;
    EditText nameEdit;
    TextView pathText;

    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private String savePath, fileName;
    private boolean isSave = false;
    private final byte INIT_STATE = 0;
    private final byte SAVE_STATE = 1;
    private final byte CLEAR_STATE = 2;
    private final byte DELETE_STATE = 3;
    private byte currentState = INIT_STATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        LogUtils.d("XIAO", INIT_STATE + "|" + SAVE_STATE + "|" + CLEAR_STATE + "|" + DELETE_STATE);

        editTitle = findViewById(R.id.content_title_id);
        editContent = findViewById(R.id.content_edit_id);
        descText = findViewById(R.id.desc_id);

        saveBtn = findViewById(R.id.save);
        clearBtn = findViewById(R.id.clear);
        deleteBtn = findViewById(R.id.delete);

        /**
         * 监听 editText 内容变化
         */
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtils.d("XIAO", s + "| start =" + start + "| after=" + after  + "| count=" + count);
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                LogUtils.d("XIAO", s + "| start =" + start + "| before=" + before  + "| count=" + count);
                final StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(Utils.fmtTime(System.currentTimeMillis()))
                        .append("  字数：").append(s.length());
                mMainHandler.removeCallbacksAndMessages(null);
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        descText.setText(stringBuffer.toString());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        saveBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                onSaveText();
                break;
            case R.id.clear:
                onClearText();
                break;
            case R.id.delete:
                onDeleteText();
                break;
            case R.id.filePath:
                onSelectPath();
                break;
            default:
                break;
        }
    }

    private void onSaveText(){
        if(editTitle.getText() == null || editTitle.getText().length() == 0){
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
        onDialogForSave();
        isSave = true;
    }

    private void onDialogForSave(){
        saveDialogView = LayoutInflater.from(this).inflate(R.layout.save_dialog_layout, null);
        nameEdit = saveDialogView.findViewById(R.id.fileName);
        pathText = saveDialogView.findViewById(R.id.filePath);
        pathText.setOnClickListener(this);
        nameEdit.setText(editTitle.getText().toString());
        pathText.setText(getApplicationContext().getExternalCacheDir().getPath());
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("保存文档")
                .setIcon(R.drawable.ic_save_red_300_24dp)
                .setView(saveDialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileName = nameEdit.getText().toString();
                        savePath = pathText.getText().toString();
                        String path = savePath + File.separator + fileName;
                        LogUtils.d("XIAO", "path =" + path);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtils.d("XIAO", "取消 =" + fileName);
                        dialog.cancel();
                    }
                });
        AlertDialog dialog =  builder.create();
        dialog.show();

    }

    private void onSelectPath(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 100);
    }

    private void onClearText(){
        editContent.setText("");
    }

    private void onDeleteText(){
        if(isSave){
            Toast.makeText(this, "将删除已保存的文件：", Toast.LENGTH_LONG).show();
            //若是已保存过，则删除文件，否则不需要
        }
        onDialogForDelete();
        editTitle.setText("");
        descText.setText("");
        editContent.setText("");
        isSave = false;
    }

    private void onDialogForDelete(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            if(uri != null && uri.getScheme().startsWith("file")){
                pathText.setText(uri.toString());
            }else if(uri != null && uri.getScheme().startsWith("content")){
                String[] projection = new String[]{MediaStore.Images.Media.DATA};
                Cursor cursor = this.managedQuery(uri, projection, null, null, null);
                try {
                    if(cursor != null && cursor.moveToFirst()){
                        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        String path = cursor.getString(index);
                        pathText.setText(path);
                    }
                }finally {
                    if(cursor != null){
                        cursor.close();
                    }
                }

            }

        }
    }
}
