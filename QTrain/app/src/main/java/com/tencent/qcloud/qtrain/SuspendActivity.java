package com.tencent.qcloud.qtrain;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuspendActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        String[] titles = new String[40];
        for(int i = 0; i < titles.length; i ++){
            titles[i] = String.valueOf(i + 1);
        }
        MyRecycleViewAdapter myRecycleViewAdapter = new MyRecycleViewAdapter(Arrays.asList(titles));
        recyclerView.setAdapter(myRecycleViewAdapter);
    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder>{

        List<String> itemDataList;

        public MyRecycleViewAdapter(List<String> itemData){
            if(itemData != null){
                this.itemDataList = itemData;
            }else {
                itemDataList = new ArrayList<>();
            }
        }

        @NonNull
        @Override
        public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                    parent, false);
            return new MyRecycleViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.MyViewHolder viewHolder, int position) {
            viewHolder.textView.setText(itemDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return itemDataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private ImageView imageView;
            private TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageId);
                textView = itemView.findViewById(R.id.textId);
            }
        }
    }
}
