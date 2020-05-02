package com.receipt.invoice.stock.sirproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.OnBoardings.OnBoarding_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signin_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signup_Activity;

public class Splash_Activity extends AppCompatActivity {
    boolean LOGGED_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE, MODE_PRIVATE);
                //if (preferences.contains(Constant.FIRST_TIME)) {

                    LOGGED_IN = preferences.getBoolean(Constant.LOGGED_IN, false);
                    if (LOGGED_IN) {

                        Intent i = new Intent(Splash_Activity.this, Home_Activity.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(Splash_Activity.this, Signin_Activity.class);
                        startActivity(i);
                    }
               // }

/*
                else {
                    Intent intent = new Intent(Splash_Activity.this, OnBoarding_Activity.class);
                    startActivity(intent);
                }
*/





            }
        },3000);



    }
}
