package com.receipt.invoice.stock.sirproject.OnBoardings;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.receipt.invoice.stock.sirproject.Adapter.CustomViewpager;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signin_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signup_Activity;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;




public class OnBoarding_Activity extends AppCompatActivity {


    ViewPager pager;
    CircleIndicator indicator;
    CustomViewpager customViewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();

    TextView txtskip;

    Button btncontinue;

    String signup="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding_);

        SharedPreferences preferences = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        preferences.edit().putString(Constant.FIRST_TIME,"f").commit();

        if (getIntent().hasExtra("signup")){
            signup = "yes";
        }


        fragments.add(new OnBooarding_Fragment1());
        fragments.add(new OnBooarding_Fragment2());
        fragments.add(new OnBooarding_Fragment3());
        fragments.add(new OnBooarding_Fragment4());

        pager = findViewById(R.id.viewPager);
        indicator = findViewById(R.id.indicator);
        txtskip = findViewById(R.id.txtskip);
        btncontinue = findViewById(R.id.btncontinue);

        customViewPager = new CustomViewpager(getSupportFragmentManager(),fragments);
        pager.setAdapter(customViewPager);

        indicator.setViewPager(pager);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){

            @Override
            public void onPageSelected(int position) {
                if(position == 3){

                    indicator.setVisibility(View.GONE);
                    txtskip.setVisibility(View.GONE);
                    btncontinue.setVisibility(View.VISIBLE);

                }
                else{
                    indicator.setVisibility(View.VISIBLE);
                    txtskip.setVisibility(View.VISIBLE);
                    btncontinue.setVisibility(View.GONE);
                }
            }
        });


        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (signup.equals("yes")){
                    Intent intent = new Intent(OnBoarding_Activity.this, Signup_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else{

                    Intent intent = new Intent(OnBoarding_Activity.this, Signin_Activity.class);
                    startActivity(intent);

                }
            }
        });


        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (signup.equals("yes")){
                    Intent intent = new Intent(OnBoarding_Activity.this, Signup_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {

                    Intent intent = new Intent(OnBoarding_Activity.this,Signin_Activity.class);
                    startActivity(intent);

                }
            }
        });


        fonts();
    }
    Boolean twice=false;
    @Override
    public void onBackPressed() {
        if (twice==true){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        twice=true;
        Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        },3000);

    }

    public void fonts()
    {
        txtskip.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Bold.otf"));
        btncontinue.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));

    }
}
