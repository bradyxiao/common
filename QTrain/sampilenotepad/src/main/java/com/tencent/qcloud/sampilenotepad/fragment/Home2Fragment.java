package com.tencent.qcloud.sampilenotepad.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.qcloud.sampilenotepad.R;
import com.tencent.qcloud.sampilenotepad.module.HomeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link Home2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home2Fragment extends Fragment {


    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private ImageButton sortBtn, searchBtn;
    private LinearLayout linearLayout;

    public Home2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home2Fragment newInstance(String param1, String param2) {
        Home2Fragment fragment = new Home2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        viewPager = view.findViewById(R.id.viewPageId);
        recyclerView = view.findViewById(R.id.recyclerViewId);
        linearLayout = view.findViewById(R.id.linerLayoutId);
        sortBtn = view.findViewById(R.id.sortBtnId);
        searchBtn = view.findViewById(R.id.searchBtnId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomeItem[] homeItems = new HomeItem[40];
        for(int i = 0; i < 40; i ++){
            homeItems[i] = new HomeItem();
            homeItems[i].contentDesc = "title_" + (i +1);
        }
        ContentRecyclerViewAdapter contentRecyclerViewAdapter
                = new ContentRecyclerViewAdapter(Arrays.asList(homeItems));
        recyclerView.setAdapter(contentRecyclerViewAdapter);

        viewPager.setOnPageChangeListener(new ViewPageChangeHandler(4, getActivity(), linearLayout));

        viewPager.setAdapter(new ViewPageAdapter());

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

    private static class ContentRecyclerViewAdapter extends RecyclerView.Adapter<ContentRecyclerViewAdapter.ViewHolder>{

        private List<HomeItem> homeItemList = new ArrayList<>();

        public ContentRecyclerViewAdapter(List<HomeItem> homeItems){
            if(homeItems != null){
                this.homeItemList.addAll(homeItems);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HomeItem homeItem = homeItemList.get(position);
            holder.textView.setText(homeItem.contentDesc);
            if(homeItem.imageUri != null){

            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return homeItemList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageView;
            private TextView textView;
            private View view;
            public ViewHolder(View itemView) {
                super(itemView);
                this.view = itemView;
                imageView = itemView.findViewById(R.id.imageId);
                textView = itemView.findViewById(R.id.contentId);
            }
        }
    }

    /**
     * 装饰viewPage
     */
    private static class ViewPageAdapter extends PagerAdapter{

        List<View> photos = new ArrayList<>();

        public ViewPageAdapter(){
           for(int i = 0; i < 4; i ++){

           }
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            //super.destroyItem(container, position, object);
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
           // return super.instantiateItem(container, position);
            View view = photos.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }

    /**
     * 控制圆点选项
     */
    private static class ViewPageChangeHandler implements ViewPager.OnPageChangeListener{

        private int size;
        private LinearLayout linearLayout;
        private List<ImageView> imageViewList;
        public ViewPageChangeHandler(int size, Context context, LinearLayout linearLayout){
            this.size = size;
            this.linearLayout = linearLayout;
            this.imageViewList = new ArrayList<>();
            for(int i = 0; i < size; i ++){
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = 10;
                layoutParams.rightMargin = 10;
                if(i == 0){
                    imageView.setBackgroundResource(R.drawable.checked_circle);
                }else {
                    imageView.setBackgroundResource(R.drawable.unchecked_circle);
                }
                this.linearLayout.addView(imageView, layoutParams);
                this.imageViewList.add(imageView);
            }
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < size; i++) {
                if ((position % size) == i) {      //如果是当前的位置就设置为红点
                    imageViewList.get(i).setBackgroundResource(R.drawable.checked_circle);
                } else {
                    imageViewList.get(i).setBackgroundResource(R.drawable.unchecked_circle);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
