package com.tencent.qcloud.sampilenotepad.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.qcloud.commonutils.log.LogUtils;
import com.tencent.qcloud.sampilenotepad.R;

import com.tencent.qcloud.sampilenotepad.fragment.dummy.DummyContent.DummyItem;
import com.tencent.qcloud.sampilenotepad.module.NoteItem;


import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<NoteItem> mValues = new ArrayList<>();
    private ItemListFragment.OnSelectItemListener listener;


    public MyItemRecyclerViewAdapter(List<NoteItem> items, ItemListFragment.OnSelectItemListener listener) {
        if(items != null){
            mValues.addAll(items);
        }
        if(listener != null){
            this.listener = listener;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.noteItem = mValues.get(position);
        String title = holder.noteItem.title  + "<br/>" + holder.noteItem.size;
        holder.titleText.setText(Html.fromHtml(title));
        holder.authorText.setText(holder.noteItem.author);
        holder.timestampText.setText(holder.noteItem.timestamp);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("XIAO", holder.noteItem.toString());
                if(listener != null){
                    listener.onSelectItem(holder.noteItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView authorText;
        private TextView timestampText;
        private NoteItem noteItem;
        private View view;

        public ViewHolder(View view) {
            super(view);
            titleText = view.findViewById(R.id.titleId);
            authorText = view.findViewById(R.id.authorId);
            timestampText = view.findViewById(R.id.timestampId);
            this.view = view;
        }

    }
}
