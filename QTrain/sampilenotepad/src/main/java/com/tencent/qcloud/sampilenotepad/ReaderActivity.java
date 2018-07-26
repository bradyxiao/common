package com.tencent.qcloud.sampilenotepad;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.content.DialogInterface.BUTTON_NEGATIVE;

public class ReaderActivity extends AppCompatActivity {

    private static ReaderHandler readerHandler;
    private static final byte READ_FINISH = 0;
    private static final byte READING = 1;

    TextView titleTextView, contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        titleTextView = findViewById(R.id.titleId);
        contentTextView = findViewById(R.id.contentId);
        String filePath = getIntent().getStringExtra(Common.FILE_PATH_EXTRA);
        readerHandler = new ReaderHandler(Looper.getMainLooper(), this);
        readerHandler.startRead(filePath);
    }


    private static class ReaderHandler extends Handler{
        ProgressDialog progressDialog;
        public ReaderHandler(Looper looper, Context context) {
            super(looper);
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("正在加载......");
            progressDialog.setIcon(R.drawable.ic_cloud_download_indigo_700_24dp);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setButton(BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        public void startRead(String filePath){
            if(filePath != null){
                Message message = obtainMessage();
                message.what = READING;
                message.obj = filePath;
                sendMessage(message);
            }
        }


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case READ_FINISH:
                    onReadFinish();
                    break;
                case READING:
                    onReading((String) msg.obj);
                    break;
            }
        }

        private void onReading(String filePath){
            progressDialog.show();
            new ReaderWork(filePath).start();
        }

        private void onReadFinish(){
            progressDialog.dismiss();
        }

        private void updateProgress(int progress){
            progressDialog.setProgress(progress);
        }
    }

    private static class ReaderWork extends Thread{
        String filePath;
        public ReaderWork(String filePath){
            this.filePath = filePath;
        }
        @Override
        public void run() {
            for(int i = 0; i < 100; i ++){
                readerHandler.updateProgress( i + 1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            readerHandler.onReadFinish();
        }
    }


}
