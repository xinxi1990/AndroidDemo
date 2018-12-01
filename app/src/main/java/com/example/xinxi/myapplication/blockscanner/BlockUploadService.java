package com.example.xinxi.myapplication.blockscanner;


import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * @author xinxi
 * 上报blockcarry日志接口
 */

public class BlockUploadService{

    private static final String TAG = "BlockUploadService";



    static class miTM implements TrustManager, X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }


    /**
     * 上报blockcarry日志
     * @param  JSONObjec格式
     * @return 是否上报成功结果
     * @throws JSONException
     **/


    public static StringBuffer reqBlockInfo(JSONObject body){

        String urlString = "";
        if (BlockConstans.dev){
            urlString = BlockConstans.dev_urlString;
        }else {
            urlString = BlockConstans.online_urlString;
        }
        HttpURLConnection connection = null;
        StringBuffer buffer = new StringBuffer();

        try {

            URL url = new URL(urlString);
            // 打开http连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            connection.setRequestMethod("POST");
            // 设置编码格式
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("cache-control", "no-cache");
            connection.setRequestProperty("Postman-Token", "c0abe493-174f-4e66-b0da-fc328fb60b9f");
            connection.setRequestProperty("User-Agent", "PostmanRuntime/6.4.1");
            connection.setRequestProperty("Accept", "*/*");
            //connection.setRequestProperty("Host", "ai.luojilab.com:18000");
            connection.setRequestProperty("accept-encoding", "gzip, deflate");
            //connection.setRequestProperty("content-length", "258");
            // 设置这个连接是否可以输出数据
            connection.setDoOutput(true);
            // 是否可以写入数据
            connection.setDoInput(true);
            //设置消息的类型
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            connection.connect();
            // 输出流，用来发送请求，http请求实际上直到这个函数里面才正式发送出去
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            //用此方法向服务器端发送数据
            dataOutputStream.write(body.toString().getBytes());
            dataOutputStream.flush();
            dataOutputStream.close();

            Log.d(TAG,"code = " + connection.getResponseCode());

            // 得到服务端的返回码是否连接成功
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String str = null;
                // BufferedReader特有功能，一次读取一行数据
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                inputStream.close();
                bufferedReader.close();
            } else {
                Log.d(TAG,"连接失败, code = " + connection.getResponseCode());
            }

        } catch (Exception e) {
            buffer.append("null");
            e.printStackTrace();
        }
        finally {
            // 使用完关闭TCP连接，释放资源
            connection.disconnect();
            return buffer;
        }

    }

}
