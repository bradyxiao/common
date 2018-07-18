package com.tencent.qcloud.sampilenotepad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AdvertiseActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView titleText;
    private ImageButton closeBtn;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        initUI();
    }

    private void initUI(){
        titleText = findViewById(R.id.title);
        closeBtn = findViewById(R.id.close);
        imageView = findViewById(R.id.image);
        String message = getIntent().getStringExtra("MESSAGE");
        titleText.setText(message);
        titleText.setTextColor(Color.BLUE);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close:
                Intent intent = new Intent(this, MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
