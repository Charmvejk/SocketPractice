package com.example.socketpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //1.创建监听指定服务器地址以及指定服务器监听的端口号
                    Socket socket = new Socket("192.168.1.103", 9080);//192.168.1.101为我这个本机的IP地址，端口号为9080.
                    //2.拿到客户端的socket对象的输出流发送给服务器数据
                    OutputStream os = socket.getOutputStream();
                    //写入要发送给服务器的数据
                    String s1 = new String("这里是你要发送到服务端的数据".getBytes(), "UTF-8");
                    os.write(s1.getBytes());
                    os.flush();
                    socket.shutdownOutput();
                    //拿到socket的输入流，这里存储的是服务器返回的数据
                    InputStream is = socket.getInputStream();
                    //解析服务器返回的数据
                    int lenght = 0;
                    byte[] buff = new byte[1024];
                    final StringBuffer sb = new StringBuffer();
                    while ((lenght = is.read(buff)) != -1) {
                        sb.append(new String(buff, 0, lenght, "UTF-8"));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //这里更新UI
                        }
                    });
                    //3、关闭IO资源（注：实际开发中需要放到finally中）
                    is.close();
                    os.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
