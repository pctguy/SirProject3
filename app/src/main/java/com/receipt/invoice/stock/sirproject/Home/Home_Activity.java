package com.receipt.invoice.stock.sirproject.Home;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Adapter.Business_Activities_Adapter;
import com.receipt.invoice.stock.sirproject.Adapter.Invoice_OverDue_Adapter;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Customer.Customer_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signup_Activity;
import com.receipt.invoice.stock.sirproject.Vendor.Vendor_Activity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class Home_Activity extends AppCompatActivity {


    RecyclerView businessactivitiesRV,invoiceoverdueRV;
    ArrayList<String> activityname = new ArrayList<>();
    ArrayList<String> activityamount = new ArrayList<>();
    ArrayList<String> overduename = new ArrayList<>();
    ArrayList<String> overduacompanyname = new ArrayList<>();

    Business_Activities_Adapter business_activities_adapter;
    Invoice_OverDue_Adapter invoice_overDue_adapter;

    ImageView menu;
    TextView hello,description,customerstxt,seeall,name1,name2,name3,name4,name5,name6,vendorstxt,vendorsseeall,vname1,vname2,vname3,vname4,vname5,vname6;
    RoundedImageView image1,image2,image3,image4,image5,image6,vimage1,vimage2,vimage3,vimage4,vimage5,vimage6;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        overridePendingTransition(R.anim.flip_out,R.anim.flip_in);

        if (getIntent().hasExtra("login")){
            messagebar();
        }

        Constant.bottomNav(Home_Activity.this,0);
        Constant.toolbar(Home_Activity.this,"");
        FindByIds();
        setListeners();
        setFonts();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        businessactivitiesRV.setLayoutManager(layoutManager);
        businessactivitiesRV.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        invoiceoverdueRV.setLayoutManager(layoutManager2);
        invoiceoverdueRV.setHasFixedSize(true);

        activityname.add("Invoices");
        activityname.add("Inventory");
        activityname.add("Estimates");

        activityamount.add("$ 2680.0");
        activityamount.add("$ 269.0");
        activityamount.add("$ 150.0");

        overduename.add("Kenneth Erickson");
        overduename.add("Charlie Jane");
        overduename.add("Peter Edward");

        overduacompanyname.add("Systems Inc.");
        overduacompanyname.add("Trade 4");
        overduacompanyname.add("Classic Corp.");

        business_activities_adapter = new Business_Activities_Adapter(Home_Activity.this,activityname,activityamount);
        businessactivitiesRV.setAdapter(business_activities_adapter);
        business_activities_adapter.notifyDataSetChanged();

        invoice_overDue_adapter = new Invoice_OverDue_Adapter(Home_Activity.this,overduename,overduacompanyname);
        invoiceoverdueRV.setAdapter(invoice_overDue_adapter);
        invoice_overDue_adapter.notifyDataSetChanged();


        String username = Constant.GetSharedPreferences(getApplicationContext(),Constant.FULLNAME);
        if (username.equals("")){
            hello.setText("Hello");
        }
        else{
            hello.setText("Hello "+username);
        }

    }

    private void FindByIds(){
        businessactivitiesRV = findViewById(R.id.businessactivitiesRV);
        invoiceoverdueRV = findViewById(R.id.invoiceoverdueRV);

        menu = findViewById(R.id.menu);
        hello = findViewById(R.id.hello);
        description = findViewById(R.id.description);
        customerstxt = findViewById(R.id.customerstxt);
        seeall = findViewById(R.id.seeall);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        name4 = findViewById(R.id.name4);
        name5 = findViewById(R.id.name5);
        name6 = findViewById(R.id.name6);
        vendorstxt = findViewById(R.id.vendorstxt);
        vendorsseeall = findViewById(R.id.vendorsseeall);
        vname1 = findViewById(R.id.vname1);
        vname2 = findViewById(R.id.vname2);
        vname3 = findViewById(R.id.vname3);
        vname4 = findViewById(R.id.vname4);
        vname5 = findViewById(R.id.vname5);
        vname6 = findViewById(R.id.vname6);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        image6 = findViewById(R.id.image6);

        vimage1 = findViewById(R.id.vimage1);
        vimage2 = findViewById(R.id.vimage2);
        vimage3 = findViewById(R.id.vimage3);
        vimage4 = findViewById(R.id.vimage4);
        vimage5 = findViewById(R.id.vimage5);
        vimage6 = findViewById(R.id.vimage6);

    }

    private void setListeners(){

        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Customer_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        vendorsseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Vendor_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFonts(){

        hello.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Bold.otf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        customerstxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        seeall.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        name1.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        name2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        name3.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        name4.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        name5.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        name6.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vendorstxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        vendorsseeall.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        vname1.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vname2.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vname3.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vname4.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vname5.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        vname6.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));

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

    public void messagebar(){
        new Flashbar.Builder(Home_Activity.this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("\nSuccess")
                .titleTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Bold.otf"))
                .titleSizeInSp(15)
                .message("Logged in successfully!")
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(getResources().getDrawable(R.drawable.success_toast_background))
                .duration(3000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(this)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }
}
