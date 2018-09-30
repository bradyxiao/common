package com.tencent.qcloud.qtrain;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.constraint.Constraints;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.av.video.effect.QavVideoEffect;
import com.tencent.qcloud.commonutils.log.LogUtils;
import com.tencent.qcloud.myapplication.MyService;
import com.tencent.qcloud.qtrain.Bluetooth.BluetoothUtil;
import com.tencent.qcloud.qtrain.webview_js.HomeActivity;

import java.lang.annotation.Target;
import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import bolts.Continuation;
import bolts.Task;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.layoutId);
        linearLayout.addView(new MyAnimatorView(this));
        grantPermission();
     //   registerForContextMenu(linearLayout);

        if(savedInstanceState != null){
            Log.d("XIAO", savedInstanceState.getString("XIAO", "NONE"));
        }else {
            Log.d("XIAO", "savedInstanceState is null");
        }

        Log.d("XIAO", "run" + System.getProperty("java.runtime.name"));

         Task<String> result = Task.call(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //Thread.sleep(6000);
                LogUtils.d("XIAO", "call");
                return "yes";
            }
        }, Task.BACKGROUND_EXECUTOR);
         result.onSuccess(new Continuation<String, Object>() {
             @Override
             public Object then(Task<String> task) throws Exception {
                 Log.d("XIAO", task.getResult());
                 LogUtils.d("XIAO", "then");
                 return null;
             }
         }, Task.UI_THREAD_EXECUTOR);

//         Intent intent = new Intent(Intent.ACTION_PICK);
//         intent.setData(ContactsContract.Contacts.CONTENT_URI);
//         startActivityForResult(intent, 200);

//        ComponentName componentName = new ComponentName("com.tencent.qcloud.myapplication", "com.tencent.qcloud.myapplication.MyService");
//        Intent intent1 = new Intent();
//        intent1.setComponent(componentName);
//        startService(intent1);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    private void  grantPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 600);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && resultCode == RESULT_OK){
            if(requestCode == 100){
                Toast.makeText(this, data.getBundleExtra("BUNDLE").getString("RESULT"),
                        Toast.LENGTH_SHORT).show();
            }else if(requestCode == 200){
                Uri uri = data.getData();
                String field[] = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
                Cursor cursor = getContentResolver().query(uri, field, null, null, null);
                if(cursor != null && cursor.moveToFirst()){
                    LogUtils.d("XIAO", "phone =" + cursor.getString(0));
                    cursor.close();
                }
            }else if(requestCode == 500){
                LogUtils.d("XIAO", "blueth");

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
                intentToSuspendActivity();
                return true;
            case 3:
                bundle.putInt("ACTION", 3);
                intentToListActivity();
                return true;
            case 4:
//               testSharePreferences();
//                write();
//                read();
                testBluteth();
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

    public void intentToSuspendActivity(){
        Intent intent = new Intent(this, SuspendActivity.class);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("XIAO", "onSaveInstanceState " + System.currentTimeMillis());
    }
    BluetoothUtil bluetoothUtil;
    public void testBluteth(){
        bluetoothUtil = new BluetoothUtil();
        bluetoothUtil.openBlue(this);
        bluetoothUtil.printInfo();
    }

    public void testNotification(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, SuspendActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 300, intent,
                    0);
            Notification notification = new NotificationCompat.Builder(this, "channelId_01")
                    .setTicker("请注意 notification")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("内容标题")
                    .setContentText("内容")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(100, notification);
            LogUtils.d("XIAO", "(<VERSION_CODES.O) notification");
        }else {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, SuspendActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 300, intent,
                    0);
            NotificationChannel notificationChannel = new NotificationChannel("channelId_01",
                    "channelName_01", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            Notification notification = new NotificationCompat.Builder(this, "channelId_01")
                    .setTicker("请注意 notification")
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("内容标题")
                    .setContentText("内容")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(100, notification);
            LogUtils.d("XIAO", "(>=VERSION_CODES.O) notification");
        }

    }

    private class MyAnimatorView extends View{

        public MyAnimatorView(Context context) {
            super(context);
            ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.test_anim);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setTarget(this);
            animator.start();
        }
    }
}
