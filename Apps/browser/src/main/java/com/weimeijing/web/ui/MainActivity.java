package com.weimeijing.web.ui;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.weimeijing.web.R;


public class MainActivity extends Activity implements View.OnClickListener {

    RelativeLayout main_layout;
    LinearLayout index_view;
    EditText index_title_edit;
    WebView  index_webView;
    LinearLayout index_bottom_menu_goback;
    LinearLayout index_bottom_menu_nogoback;
    LinearLayout index_bottom_menu_goforward;
    LinearLayout index_bottom_menu_nogoforward;
    LinearLayout index_bottom_menu_gohome;
    LinearLayout index_bottom_menu_nogohome;
    FrameLayout index_bottom_menu_more;
    ImageView index_title_refresh;
    ProgressBar index_title_progress;

    LinearLayout bottom_dialog;
    View index_background;

    RelativeLayout popup_exit;
    RelativeLayout popup_setting;




    LinearLayout search_view;
    EditText search_title_edit;
    Button search_title_cancel;
    ImageView search_title_url_clear;
    Button search_title_go;

    WebViewClient homeWebViewClient;
    WebChromeClient homeWebChromeClient;

    //主页地址
    private String home_url = "http://www.baidu.com";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        index_view = (LinearLayout)findViewById(R.id.index_view);
        index_title_edit = (EditText) index_view.findViewById(R.id.index_title_edit);
        index_webView = (WebView)index_view.findViewById(R.id.index_webView);
        index_bottom_menu_goback = (LinearLayout)findViewById(R.id.index_bottom_menu_goback);
        index_bottom_menu_nogoback = (LinearLayout)findViewById(R.id.index_bottom_menu_nogoback);
        index_bottom_menu_goforward = (LinearLayout)findViewById(R.id.index_bottom_menu_goforward);
        index_bottom_menu_nogoforward = (LinearLayout)findViewById(R.id.index_bottom_menu_nogoforward);
        index_bottom_menu_gohome = (LinearLayout)findViewById(R.id.index_bottom_menu_gohome);
        index_bottom_menu_nogohome = (LinearLayout)findViewById(R.id.index_bottom_menu_nogohome);
        index_bottom_menu_more = (FrameLayout)findViewById(R.id.index_bottom_menu_more);
        index_title_refresh = (ImageView)index_view.findViewById(R.id.index_title_refresh);
        index_title_progress = (ProgressBar)index_view.findViewById(R.id.index_title_progress);
        index_background = (View)findViewById(R.id.index_background);
        bottom_dialog = (LinearLayout)findViewById(R.id.bottom_dialog);
        popup_exit = (RelativeLayout)findViewById(R.id.popup_exit);
        popup_setting = (RelativeLayout)findViewById(R.id.popup_setting);




        index_bottom_menu_more.setOnClickListener(this);
        index_bottom_menu_gohome.setOnClickListener(this);
        index_title_edit.setOnClickListener(this);
        index_bottom_menu_goback.setOnClickListener(this);
        index_bottom_menu_goforward.setOnClickListener(this);
        index_title_refresh.setOnClickListener(this);
        index_background.setOnClickListener(this);
        popup_exit.setOnClickListener(this);
        popup_setting.setOnClickListener(this);





        search_view = (LinearLayout)findViewById(R.id.search_view);
        search_title_edit = (EditText)search_view.findViewById(R.id.search_title_edit);
        search_title_cancel = (Button)search_view.findViewById(R.id.search_title_cancel);
        search_title_go = (Button)search_view.findViewById(R.id.search_title_go);
        search_title_url_clear = (ImageView)search_view.findViewById(R.id.search_title_url_clear);
        search_title_edit.addTextChangedListener(search_title_edit_changed);
        search_title_cancel.setOnClickListener(this);
        search_title_url_clear.setOnClickListener(this);
        search_title_go.setOnClickListener(this);



        initHome();
        initAd();
    }



    TextWatcher search_title_edit_changed = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //是否显示清除
            if(search_title_edit.getText().toString().length()>0){
                search_title_url_clear.setVisibility(View.VISIBLE);
                search_title_go.setVisibility(View.VISIBLE);
                search_title_cancel.setVisibility(View.GONE);
            }else{
                search_title_url_clear.setVisibility(View.GONE);
                search_title_go.setVisibility(View.GONE);
                search_title_cancel.setVisibility(View.VISIBLE);
            }

            //是否显示前往
            //是否显示取消


        }
    };


    private void initHome(){
        WebSettings webSettings = index_webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);

        homeWebViewClient = new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                index_title_progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(url.equals(home_url+"/")){
                    index_bottom_menu_gohome.setVisibility(View.GONE);
                    index_bottom_menu_nogohome.setVisibility(View.VISIBLE);
                }else{
                    index_bottom_menu_gohome.setVisibility(View.VISIBLE);
                    index_bottom_menu_nogohome.setVisibility(View.GONE);
                }
                if(index_webView.canGoForward()){
                    index_bottom_menu_goforward.setVisibility(View.VISIBLE);
                    index_bottom_menu_nogoforward.setVisibility(View.GONE);
                }else{
                    index_bottom_menu_goforward.setVisibility(View.GONE);
                    index_bottom_menu_nogoforward.setVisibility(View.VISIBLE);
                }
                if(index_webView.canGoBack()){
                    index_bottom_menu_goback.setVisibility(View.VISIBLE);
                    index_bottom_menu_nogoback.setVisibility(View.GONE);
                }else{
                    index_bottom_menu_goback.setVisibility(View.GONE);
                    index_bottom_menu_nogoback.setVisibility(View.VISIBLE);
                }
            }
        };

        homeWebChromeClient = new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    index_title_progress.setVisibility(View.GONE);
                }else{
                    index_title_progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        };



        index_webView.setWebChromeClient(homeWebChromeClient);
        index_webView.setWebViewClient(homeWebViewClient);
        index_webView.loadUrl(home_url);
    }


    @Override
    public void onClick(View v) {
        InputMethodManager inputMethodManager=(InputMethodManager)search_title_edit.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()){
            case R.id.index_title_edit:
                index_view.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);
                search_title_edit.requestFocus();
                inputMethodManager.toggleSoftInput(0,InputMethodManager.SHOW_FORCED);
                break;
            case R.id.search_title_cancel:
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                index_view.setVisibility(View.VISIBLE);
                search_view.setVisibility(View.GONE);
                search_title_edit.clearFocus();
                break;
            case R.id.search_title_go:
                String goUrl = search_title_edit.getText().toString();
                if(goUrl.indexOf("http://")<0){
                    goUrl="http://"+goUrl;
                    search_title_edit.setText(goUrl);
                }
                search_title_cancel.callOnClick();
                index_webView.loadUrl(goUrl);
                break;
            case R.id.index_bottom_menu_goback:
                index_webView.goBack();
                break;
            case R.id.index_bottom_menu_goforward:
                index_webView.goForward();
                break;
            case R.id.index_bottom_menu_gohome:
                index_webView.loadUrl(home_url);
                break;
            case R.id.index_bottom_menu_more:
                //动画设置有问题
                if(bottom_dialog.getVisibility()==View.GONE){
                    //Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.menu_show);
                    index_background.setVisibility(View.VISIBLE);
                    //bottom_dialog.startAnimation(animation);
                    bottom_dialog.setVisibility(View.VISIBLE);
                    //bottom_dialog.setAnimation();

                }else{
                    //bottom_dialog.setAnimation(AnimationUtils.loadAnimation(this,R.anim.menu_hide));
                    index_background.setVisibility(View.GONE);
                    bottom_dialog.setVisibility(View.GONE);

                }
                break;
            case R.id.search_title_url_clear:
                search_title_edit.setText("");
                break;
            case R.id.index_title_refresh:
                index_webView.reload();
                break;
            case R.id.index_background:
                bottom_dialog.setVisibility(View.GONE);
                index_background.setVisibility(View.GONE);
                break;
            case R.id.popup_exit:
                new AlertDialog.Builder(this).setTitle("确认退出吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                MainActivity.this.finish();
                                System.exit(0);
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
                break;
            case R.id.popup_setting:
                Intent intent=new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && index_webView.canGoBack()) {
            index_webView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        //finish();//结束退出程序
        return false;
    }


    //连续按2次退出程序
    private long exitTime;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    private void initAd(){
        initBannerAd();
        initInterstitialAD();

    }

    private void initBannerAd(){
        //广告1
        RelativeLayout l = (RelativeLayout)findViewById(R.id.bottom_more_bannerAd);
        BannerView banner = new BannerView(this, ADSize.BANNER, "1103198930", "3080308062175737");
        banner.setRefresh(30);
        banner.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(int i) {
                Log.i("AD_DEMO", "BannerNoAD，eCode=" + i);
            }
            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        banner.loadAD();
        l.addView(banner);
    }

    private void initInterstitialAD(){
        final InterstitialAD iad = new InterstitialAD(this, "1103198930", "3000903056155648");
        iad.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onADReceive() {
                iad.show();
            }
            @Override
            public void onNoAD(int i) {}
        });
        iad.loadAD();
    }
}
