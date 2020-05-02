package com.receipt.invoice.stock.sirproject.SignupSignin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.ForgotResetPassword.ForgotPassword_Activity;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.OnBoardings.OnBoarding_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Signin_Activity extends AppCompatActivity {


    ImageView imgback;

    TextView txtsignin,txtsignindes,txtforgotpassword,txtaccount,txtsignuphere;
    EditText edemail,edpassword;
    Button btnlogin;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);

        imgback = findViewById(R.id.imgback);
        txtsignin = findViewById(R.id.txtsignin);
        txtsignindes = findViewById(R.id.txtsignindes);
        txtforgotpassword = findViewById(R.id.txtforgotpassword);
        txtaccount = findViewById(R.id.txtaccount);
        txtsignuphere = findViewById(R.id.txtsignuphere);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btnlogin);


        txtsignuphere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin_Activity.this,OnBoarding_Activity.class);
                intent.putExtra("signup","signup");
                startActivity(intent);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin_Activity.this,Signup_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //net condition
                Signin();
            }
        });

        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin_Activity.this, ForgotPassword_Activity.class);
                startActivity(intent);
            }
        });

        fonts();

    }
    public void fonts()
    {
        txtsignin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Bold.otf"));
        txtsignindes.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtforgotpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtaccount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtsignuphere.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        btnlogin.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));

    }

    public void Signin()
    {
        final SharedPreferences pref = getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        if (TextUtils.isEmpty(edemail.getText())) {
            edemail.setError("Field is required");
            edemail.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(edemail.getText().toString()).matches())
        {
            edemail.setError("Invalid Pattern");
            edemail.requestFocus();
        }

        else if (TextUtils.isEmpty(edpassword.getText())) {
            edpassword.setError("Field is required");
            edpassword.requestFocus();
        } else
            {

                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);


                String email = edemail.getText().toString();
                String password = edpassword.getText().toString();

                RequestParams params = new RequestParams();

                params.add("email",email);
                params.add("password",password);

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Constant.BASE_URL+"user/login",params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        String response = new String(responseBody);

                        Log.e("signinresponse",response);

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            if (status.equals("true"))
                            {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String access_token = data.getString("access_token");
                                JSONObject profile = data.getJSONObject("profile");
                                String fullname = profile.getString("fullname");
                                String email = profile.getString("email");
                                pref.edit().putString(Constant.ACCESS_TOKEN,access_token).commit();
                                pref.edit().putString(Constant.FULLNAME,fullname).commit();
                                pref.edit().putString(Constant.EMAIL,email).commit();
                                pref.edit().putBoolean(Constant.LOGGED_IN,true).commit();

                                Constant.SuccessToast(Signin_Activity.this,jsonObject.getString("message"));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Signin_Activity.this, Home_Activity.class);
                                        intent.putExtra("login","login");
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                },1500);



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if(responseBody!=null){
                            String response = new String(responseBody);
                            Log.e("signinresponseF",response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                String status = jsonObject.getString("status");
                                if (status.equals("false"))
                                {

                                    //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                    Constant.ErrorToast(Signin_Activity.this,jsonObject.getString("message"));


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);

                    }
                });


        }
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
}
