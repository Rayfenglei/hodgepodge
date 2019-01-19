package com.example.ray.myapplication.NetWork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ray.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientActivity extends AppCompatActivity implements Runnable{

    private TextView textShow;
    private Button sendBtn;
    private EditText sendEdit;
    private static final String HOST="192.168.168.117";
    private static final int PORT = 30000;
    private Socket socket =null;
    private BufferedReader in =null;
    private PrintWriter out =null;
    private String content = "";
    private StringBuilder sb =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ContentShow();
        sb = new StringBuilder();
        //当程序一开始运行的时候就实例化Socket对象,与服务端进行连接,获取输入输出流
        //不能主线程中进行网络操作,所以需要另外开辟一个线程
        Log.i("socket",""+HOST);
        new Thread(){
            public void run(){
                try {
                    socket = new Socket(HOST,PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                }catch (IOException e){e.printStackTrace();}
            }

        }.start();

        //为发送按钮设置点击事件
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = sendEdit.getText().toString();
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        out.println(msg);
                    }
                }
            }
        });
        new Thread(ClientActivity.this).start();

        SharedPreferences preferences = getSharedPreferences("data",Context.MODE_PRIVATE);
        int data = preferences.getInt("data",0);
        Log.i("data",""+data);
    }

    //重写run方法,在该方法中输入流的读取
    @Override
    public void run() {
        try {
            while (true) {
                if (socket.isConnected()) {
                    if (!socket.isInputShutdown()) {
                        if ((content = in.readLine()) != null) {
                            content += "\n";
                            handler.sendEmptyMessage(0x123);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定义一个handler对象,用来刷新界面
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                sb.append(content);
                textShow.setText(sb.toString());
            }
        }
    };

    public void ContentShow(){
        textShow =  findViewById(R.id.txtshow);
        sendEdit =  findViewById(R.id.editsend);
        sendBtn =  findViewById(R.id.button_send);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
