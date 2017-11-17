package com.weimeijing.web.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.weimeijing.web.R;

/**
 * Created by chenjie on 2016/11/5.
 */
public class WelcomeActivity extends Activity implements SplashADListener {

    private SplashAD splashAD;
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        container = (ViewGroup) this.findViewById(R.id.splash_container);
        splashAD = new SplashAD(this, container, this.getString(R.string.qq_appid), this.getString(R.string.qq_splash), this,100000);
    }

    @Override
    public void onADPresent() {
        Log.i("AD_DEMO", "SplashADPresent");
    }



    @Override
    public void onADClicked() {

    }



    private void next() {
        this.startActivity(new Intent(this, MainActivity.class));
        //防止用户回退看到此页面
        this.finish();
    }

    @Override
    public void onADDismissed() {
        Log.i("AD_DEMO", "SplashADDismissed");
        next();
    }


    @Override
    public void onNoAD(int arg0) {
        Log.i("AD_DEMO", "LoadSplashADFail,ecode=" + arg0);
        next();
    }
    //防止用户返回键退出APP
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
