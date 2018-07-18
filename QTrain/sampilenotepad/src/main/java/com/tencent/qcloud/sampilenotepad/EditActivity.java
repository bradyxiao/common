package com.tencent.qcloud.sampilenotepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    EditText editTitle, editContent;
    TextView descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTitle = findViewById(R.id.content_title_id);
        editContent = findViewById(R.id.content_edit_id);
        descText = findViewById(R.id.desc_id);
    }
}
