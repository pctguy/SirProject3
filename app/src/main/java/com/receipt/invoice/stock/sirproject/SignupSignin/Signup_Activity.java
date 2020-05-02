package com.receipt.invoice.stock.sirproject.SignupSignin;

import android.content.Intent;
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

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.OnBoardings.OnBoarding_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.Splash_Activity;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Signup_Activity extends AppCompatActivity {


    TextView txtsignup,txtsignupdes,txtregistered,txtsigninhere;
    EditText edfullname,edemail,edpassword;
    Button btnregister;

    String mail="";

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    ImageView imgback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        txtsignup = findViewById(R.id.txtsignup);
        txtsignupdes = findViewById(R.id.txtsignupdes);
        txtregistered = findViewById(R.id.txtregistered);
        txtsigninhere = findViewById(R.id.txtsigninhere);
        edfullname = findViewById(R.id.edfullname);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        btnregister = findViewById(R.id.btnregister);
        imgback = findViewById(R.id.imgback);

        fonts();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup_Activity.this.onBackPressed();
            }
        });

        txtsigninhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_Activity.this,Signin_Activity.class);
                startActivity(intent);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                userregistration();

               // messagebar();
            }
        });


    }


    public void fonts()
    {
        txtsignup.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Bold.otf"));
        txtsignupdes.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtregistered.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Light.ttf"));
        txtsigninhere.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        edfullname.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        edemail.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        edpassword.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        btnregister.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));

    }




 public void userregistration()
 {
     if (TextUtils.isEmpty(edfullname.getText())) {
     edfullname.setError("Field is required");
     edfullname.requestFocus();
    }
     else if (TextUtils.isEmpty(edemail.getText())) {
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
     } else {

         avi.smoothToShow();
         avibackground.setVisibility(View.VISIBLE);


         String fullname = edfullname.getText().toString();
         String email = edemail.getText().toString();
         final String password = edpassword.getText().toString();
         RequestParams requestParams = new RequestParams();
         requestParams.add("email",email);
         requestParams.add("password",password);
         requestParams.add("fullname",fullname);


         AsyncHttpClient client = new AsyncHttpClient();
         client.post(Constant.BASE_URL+"user/registration", requestParams, new AsyncHttpResponseHandler() {
             @Override
             public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                 String response = new String(responseBody);
                 Log.e("signupresponse",response);

                 avi.smoothToHide();
                 avibackground.setVisibility(View.GONE);
                 try {
                     JSONObject jsonObject = new JSONObject(response);

                     String status = jsonObject.getString("status");

                     if (status.equals("true"))
                     {
                         JSONObject data = jsonObject.getJSONObject("data");
                         JSONObject user = data.getJSONObject("user");

                          mail = user.getString("email");
                          final String verification_code = user.getString("verification_code");

                         Constant.SuccessToast(Signup_Activity.this,jsonObject.getString("message"));
                         new Handler().postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 Intent intent = new Intent(Signup_Activity.this,Verification_Code_Activity.class);
                                 intent.putExtra("mail",mail);
                                 intent.putExtra("pwd",password);
                                 intent.putExtra("verification_code",verification_code);
                                 startActivity(intent);
                             }
                         },1500);


                     }





                 } catch (JSONException e) {
                     e.printStackTrace();
                 }


                 // messagebar();

             }

             @Override
             public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                 if(responseBody!=null){
                     String response = new String(responseBody);
                     Log.e("signupresponseF",response);

                     try {
                         JSONObject jsonObject = new JSONObject(response);

                         String status = jsonObject.getString("status");
                         if (status.equals("false"))
                         {

                             //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                             Constant.ErrorToast(Signup_Activity.this,jsonObject.getString("message"));



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

}
