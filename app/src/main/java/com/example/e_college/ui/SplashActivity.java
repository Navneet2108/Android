package com.example.e_college.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.e_college.R;

public class SplashActivity extends AppCompatActivity {
    TextView txtTitle;
    void initViews(){
        txtTitle=findViewById(R.id.textTitle);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        initViews();
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        txtTitle.startAnimation(animation);
        handler.sendEmptyMessageDelayed(101, 3000);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
