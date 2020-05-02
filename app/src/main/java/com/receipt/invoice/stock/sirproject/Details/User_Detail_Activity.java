package com.receipt.invoice.stock.sirproject.Details;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.wang.avi.AVLoadingIndicatorView;

public class User_Detail_Activity extends AppCompatActivity {
    TextView name,email,roles;
    RoundedImageView image;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__detail_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Constant.toolbar(User_Detail_Activity.this,"Details");
        Constant.bottomNav(User_Detail_Activity.this,0);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        FindByIds();
        setFonts();
        setListeners();
    }
    private void FindByIds(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        image = findViewById(R.id.image);
        roles = findViewById(R.id.roles);
    }

    private void setFonts(){

        name.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Medium.otf"));
        email.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));
        roles.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));
    }

    private void setListeners(){


    }
}
