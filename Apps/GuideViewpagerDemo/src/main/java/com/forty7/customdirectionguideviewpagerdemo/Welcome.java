package com.forty7.customdirectionguideviewpagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Welcome extends Activity
{
    private VerticalLinearLayout mMianLayout;
    private Button jump,over;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mMianLayout = (VerticalLinearLayout) findViewById(R.id.id_main_ly);
        jump = (Button) findViewById(R.id.jump);
        over = (Button) findViewById(R.id.over);

        jump.setOnClickListener(new View.OnClickListener() {//跳过
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this,MainActivity.class));
                finish();
            }
        });
        over.setOnClickListener(new View.OnClickListener() {//开始使用
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome.this,MainActivity.class));
                finish();
            }
        });

        mMianLayout.setOnPageChangeListener(new VerticalLinearLayout.OnPageChangeListener() {//滑动监听
            @Override
            public void onPageChange(int currentPage) {
                mMianLayout.getChildAt(currentPage);
                //Toast.makeText(Welcome.this, "第"+(currentPage+1)+"页", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
