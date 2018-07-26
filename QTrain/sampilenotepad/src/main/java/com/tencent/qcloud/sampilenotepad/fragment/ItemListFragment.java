package com.tencent.qcloud.sampilenotepad.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.qcloud.sampilenotepad.R;
import com.tencent.qcloud.sampilenotepad.fragment.dummy.DummyContent;
import com.tencent.qcloud.sampilenotepad.module.NoteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ItemListFragment extends Fragment {

    private OnSelectItemListener listener;

    public ItemListFragment() {
    }

    public void setListener(OnSelectItemListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            List<NoteItem> noteItemList = new ArrayList<>();
            NoteItem noteItem = new NoteItem();
            noteItem.title = "title1";
            noteItem.size = 1;
            noteItem.author = "xiao";
            noteItem.timestamp = "22:00:12";
            noteItemList.add(noteItem);

            noteItem = new NoteItem();
            noteItem.title = "title2";
            noteItem.size = 2;
            noteItem.author = "xiao";
            noteItem.timestamp = "22:02:12";
            noteItemList.add(noteItem);


            noteItem = new NoteItem();
            noteItem.title = "title3";
            noteItem.size = 3;
            noteItem.author = "xiao";
            noteItem.timestamp = "22:03:12";
            noteItemList.add(noteItem);


            noteItem = new NoteItem();
            noteItem.title = "title4";
            noteItem.size = 4;
            noteItem.author = "xiao";
            noteItem.timestamp = "22:04:12";
            noteItemList.add(noteItem);
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(noteItemList, listener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( !(context instanceof OnSelectItemListener)){
            throw new RuntimeException("activity which is attached by this fragment must implement OnSelectItemListener");
        }else {
            setListener((OnSelectItemListener) context);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public static interface OnSelectItemListener{
        void onSelectItem(NoteItem noteItem);
    }
}
