package com.receipt.invoice.stock.sirproject.Details;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.TextView;

import com.crystalviewpager.widgets.CrystalViewPager;
import com.receipt.invoice.stock.sirproject.Adapter.CustomViewPagerAdapter;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Customer.Add_Customer;
import com.receipt.invoice.stock.sirproject.Customer.Customer_Activity;
import com.receipt.invoice.stock.sirproject.Customer.Customer_Listing;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.R;

public class Company_Details_Activity extends AppCompatActivity {
    CrystalViewPager viewPager;
    TabLayout tabs;

    //String company_id,company_name,company_image,company_email,company_phone,company_website,company_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company__details_);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Constant.toolbar(Company_Details_Activity.this,"Details");
        Constant.bottomNav(Company_Details_Activity.this,1);

        viewPager = findViewById(R.id.viewPagerLayout);
        tabs = findViewById(R.id.tabs);

        setUpViewPager(viewPager);

        tabs.setupWithViewPager(viewPager);

        TextView customers = (TextView) LayoutInflater.from(this).inflate(R.layout.tabview, null);
        TextView addcustomer = (TextView) LayoutInflater.from(this).inflate(R.layout.tabview, null);
        TextView addwarehouse = (TextView) LayoutInflater.from(this).inflate(R.layout.tabview, null);

        customers.setTextColor(getResources().getColor(R.color.lightpurple));
        addcustomer.setTextColor(getResources().getColor(R.color.lightpurple));
        addwarehouse.setTextColor(getResources().getColor(R.color.lightpurple));
        customers.setText("COMPANY");
        addcustomer.setText("WAREHOUSE (S)");
        addwarehouse.setText("ADD WAREHOUSE");

        customers.setTextSize(12);
        addcustomer.setTextSize(12);
        addwarehouse.setTextSize(12);

        customers.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        addcustomer.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        addwarehouse.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));

        tabs.getTabAt(0).setCustomView(customers);
        tabs.getTabAt(1).setCustomView(addwarehouse);
        tabs.getTabAt(2).setCustomView(addcustomer);

        if (getIntent().hasExtra("warehouse")){
            viewPager.setCurrentItem(2);
        }

    }

    private void setUpViewPager(ViewPager pager) {

        CustomViewPagerAdapter adapter = new CustomViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Company_Details_Fragment(), "COMPANY");
        adapter.addFragment(new Add_Warehouse_Fragment(), "ADD WAREHOUSE");
        adapter.addFragment(new Warehouse_Listing_Fragment(), "WAREHOUSE (S)");
        pager.setAdapter(adapter);

    }
}
