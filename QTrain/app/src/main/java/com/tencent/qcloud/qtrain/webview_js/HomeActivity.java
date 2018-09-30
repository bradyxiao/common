package com.tencent.qcloud.qtrain.webview_js;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.qtrain.R;
import com.tencent.qcloud.qtrain.httpdns.HttpDNSManager;

import java.io.IOException;

public class HomeActivity extends Activity {

   // WebView webView;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //webView = findViewById(R.id.webviewId);
        textView = findViewById(R.id.exitId);

        Bundle bundle = getIntent().getBundleExtra("BUNDLE");
        int action = bundle.getInt("ACTION");
        switch (action){
            case 1:
                testloadUrl();
                break;
            case 2:
                testDNS();
                break;
        }

        registerForContextMenu(textView);
    }

    public void testloadUrl(){
        String url = "file:///android_asset/js/demo.html";
        //webView.loadUrl(url);
    }

    public void testDNS(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpDNSManager.test(HomeActivity.this.getApplicationContext(), "www.baid.com");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "EXIT");

        menu.setGroupCheckable(0, true, true);
        menu.setHeaderTitle("EXIT");
        menu.setHeaderIcon(R.drawable.ic_exit_24dp);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()){
            case 1:
                bundle.putString("RESULT", "测试正确率 100%");
                exit(bundle);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void exit(Bundle bundle){
//        Intent intent = new Intent(this, HuaWeiActivity.class);
//        intent.putExtra("BUNDLE", bundle);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterForContextMenu(textView);
    }
}
