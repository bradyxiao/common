package com.tencent.qcloud.qtrain.httpdns;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by bradyxiao on 2018/7/17.
 *
 * @author bradyxiao
 * @Time 2018/7/17
 */

public class HttpDNSManager {

    public static void test(Context context, String doname) throws IOException {

        String sourceIp = getLocalIp(context);
        Log.d("XIAO", sourceIp);

        String urlString = "http://119.29.29.29/d?dn=" +doname + "&ip=" + sourceIp;
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        Log.d("XIAO", "REQUEST ************************************************");
        Log.d("XIAO", httpURLConnection.getRequestMethod() + "|" + httpURLConnection.getURL().toString());
        Map<String, List<String>> requestHeaders = httpURLConnection.getRequestProperties();
        if(requestHeaders != null){
            for(Map.Entry<String, List<String>> entry : requestHeaders.entrySet()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(entry.getKey()).append(":");
                for(String string : entry.getValue()){
                    stringBuilder.append(string).append(";");
                }
                Log.d("XIAO", stringBuilder.toString());

            }
        }
        httpURLConnection.connect();
        Log.d("XIAO", "RESPONSE *************************************************");

        Log.d("XIAO", httpURLConnection.getResponseCode() + "|" + httpURLConnection.getResponseMessage());
        Map<String, List<String>> headers = httpURLConnection.getHeaderFields();
        if(headers != null){
            for(Map.Entry<String, List<String>> entry : headers.entrySet()){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(entry.getKey()).append(":");
                for(String string : entry.getValue()){
                    stringBuilder.append(string).append(";");
                }
                Log.d("XIAO", stringBuilder.toString());
            }
        }
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            while (line != null){
                Log.d("XIAO", line);
                line = bufferedReader.readLine();
            }
        }catch (IOException e){
            throw e;
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(bufferedReader != null){
                bufferedReader.close();
            }

        }
    }

    public static String getLocalIp(Context context){
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
