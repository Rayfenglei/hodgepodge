package com.example.f2846843.myapplication.Web;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.f2846843.myapplication.R;

//利用返回键返回上一页，但是不能退出当前activity

public class GetUrlActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mImageBtn,mWebBtn;
    private WebView webView;
    private ImageView imageView;
    private long exitTime = 0;
    private String webViewUrl = "http://www.baidu.com/";
    private WebSettings webSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_url);
        init();

    }

    private void init(){
        mImageBtn = findViewById(R.id.get_image_btn);
        mWebBtn = findViewById(R.id.get_web_btn);
        imageView = findViewById(R.id.get_image_view);
        webView = findViewById(R.id.get_web_view);

        mImageBtn.setOnClickListener(this);
        mWebBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.get_image_btn:
                if (imageView.getVisibility()==View.GONE||webView.getVisibility()==View.VISIBLE){
                    webView.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    imageViewShow();

                }else {
                    imageView.setVisibility(View.GONE);
                }

                break;
            case R.id.get_web_btn:

                if (webView.getVisibility()==View.GONE||imageView.getVisibility()==View.VISIBLE){
                    imageView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                    webViewShow();
                }else {
                    webView.setVisibility(View.GONE);
                }


                break;
            default:
                break;
        }
    }

    private void webViewShow(){

        webView.loadUrl(webViewUrl);
        webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webView.setWebViewClient(new WebViewClient() {
            //打开网页时不调用系统浏览器， 而是在本WebView中显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //防止重定向
                return false;
            }
        });

        //获取网站的信息 进度，标题框等等
        webView.setWebChromeClient(new WebChromeClient(){
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });


    }
    private void imageViewShow(){
        //本地图片
        //imageView.setImageResource(R.mipmap.ic_launcher_round);
        //"http://img2.3lian.com/2014/c7/25/d/40.jpg"
        //"http://img2.3lian.com/2014/c7/25/d/41.jpg"
        //"http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"
        //"http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"
        Glide.with(GetUrlActivity.this).load("https://pic.cnblogs.com/avatar/1142647/20170416093225.png").into(imageView);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //判断是否能返回上一页
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        webView.destroy();
        finish();
    }
}
