package com.tencent.qcloud.qtrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.qcloud.qtrain.webview_js.HomeActivity;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.layoutId);
        registerForContextMenu(linearLayout);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && resultCode == RESULT_OK){
            if(requestCode == 100){
                Toast.makeText(this, data.getBundleExtra("BUNDLE").getString("RESULT"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //创建上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "测试一");
        menu.add(0, 2, 0, "测试二");
        menu.add(0, 3, 0, "测试三");
        menu.add(0, 4, 0, "测试四");

        menu.setGroupCheckable(0, true, true);
        menu.setHeaderIcon(R.drawable.ic_selecet_24dp);
        menu.setHeaderTitle("选择一个测试");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case 1:
                bundle.putInt("ACTION", 1);
                intentToHomeActivity(bundle);
                return true;
            case 2:
                bundle.putInt("ACTION", 2);
                intentToHomeActivity(bundle);
                return true;
            case 3:
                bundle.putInt("ACTION", 3);
                intentToListActivity();
                return true;
            case 4:
               testSharePreferences();
                write();
                read();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void intentToHomeActivity(Bundle bundle){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("BUNDLE", bundle);
        startActivityForResult(intent, 100);
    }

    public void intentToListActivity(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(linearLayout);
    }

    /**
     * 测试多线程 shareprefered
     */

    public void write(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("YES", true);
        editor.putBoolean("NO", false);
        editor.putString("YES", "true_yes");
        editor.commit();
        Log.d("XIAO", "wrtie over");
    }

    public void read(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("test", MODE_PRIVATE);
        String yes = sharedPreferences.getString("YES", "false");
        Boolean no = sharedPreferences.getBoolean("NO", true);
        Log.d("XIAO", "yes =" + yes + "| no =" + no);
    }

    public void testSharePreferences(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                write();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        read();
                    }
                }).start();
            }
        }).start();



    }
}
