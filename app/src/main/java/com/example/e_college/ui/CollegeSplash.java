package com.example.e_college.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_college.R;

import java.util.Locale;

public class CollegeSplash extends AppCompatActivity {
    String TAG="CollegeSplash";
    TextToSpeech tts;

    TextView txtTitle;

    void initViews(){
        txtTitle=findViewById(R.id.textViewTitle);

        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(tts.getEngines().size() == 0){
                    Toast.makeText(CollegeSplash.this, "Please enable TTS in your settings", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    tts.setLanguage(Locale.US);
                    speak("Hey!Welcome to e-college");
                    //speak("Enter your name");
                }
            }
        });


    }
    private void speak(String s) {
        //if(Build.VERSION.SDK_INT >=21){
        tts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        /*}else{
            tts.speak(s,TextToSpeech.QUEUE_FLUSH,null);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i(TAG,"Some error");
        setContentView(R.layout.activity_college_splash);
        getSupportActionBar().hide();
        initViews();
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        txtTitle.startAnimation(animation);

        handler.sendEmptyMessageDelayed(101,5000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 101){
                Intent intent = new Intent(CollegeSplash.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
