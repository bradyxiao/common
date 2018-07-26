package com.tencent.qcloud.qtrain;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView = findViewById(R.id.recycleViewId);

        List<ItemData> itemDataList = new ArrayList<>();
        ItemData itemData = new ItemData();
        itemData.imageId = R.drawable.ic_mood_black_24dp;
        itemData.text = "微笑";
        itemDataList.add(itemData);
        itemData = new ItemData();
        itemData.imageId = R.drawable.ic_exit_24dp;
        itemData.text = "退出";
        itemDataList.add(itemData);
        itemData = new ItemData();
        itemData.imageId = R.drawable.ic_exit_24dp;
        itemData.text = "退出";
        itemDataList.add(itemData);
        itemData = new ItemData();
        itemData.imageId = R.drawable.ic_exit_24dp;
        itemData.text = "退出";
        itemDataList.add(itemData);

        MyRecycleViewAdapter myRecycleViewAdapter = new MyRecycleViewAdapter(itemDataList);

        //设置 recyclerView的显示列表形式，不像ListView只能竖直显示
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //设置 recyclerView 的Adapter
        recyclerView.setAdapter(myRecycleViewAdapter);
    }

    private class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder>{

        List<ItemData> itemDataList;

        public MyRecycleViewAdapter(List<ItemData> itemData){
            if(itemData != null){
                this.itemDataList = itemData;
            }else {
                itemDataList = new ArrayList<>();
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                    parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.MyViewHolder viewHolder, int position) {
            viewHolder.imageView.setImageResource(itemDataList.get(position).imageId);
            viewHolder.textView.setText(itemDataList.get(position).text);
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
