package com.receipt.invoice.stock.sirproject.Invoice;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.receipt.invoice.stock.sirproject.Adapter.Products_Adapter;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;

public class Create_Invoice_Activity extends AppCompatActivity {


    TextView invoicenumtxt,invoicenum,duedatetxt,duedate,invoicetotxt,invoiceto,itemstxt,subtotaltxt,subtotal,discounttxt,discount,taxtxt,tax;
    Button additem,createinvoice,options;
    RecyclerView productsRecycler;
    Products_Adapter products_adapter;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__invoice_);
        //Constant.bottomNav(Create_Invoice_Activity.this,1);

        overridePendingTransition(R.anim.flip_out,R.anim.flip_in);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Constant.toolbar(Create_Invoice_Activity.this,"Create Invoice");

        FindByIds();
        setFonts();
        setListeners();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        productsRecycler.setLayoutManager(layoutManager);
        productsRecycler.setHasFixedSize(true);

        names.add("Product 1");

        quantity.add("11");

        price.add("$ 100");

        products_adapter = new Products_Adapter(Create_Invoice_Activity.this,names,quantity,price);
        productsRecycler.setAdapter(products_adapter);
        products_adapter.notifyDataSetChanged();


    }

    private void FindByIds(){
        invoicenumtxt = findViewById(R.id.invoicenumtxt);
        invoicenum = findViewById(R.id.invoivenum);
        duedatetxt = findViewById(R.id.duedatetxt);
        duedate = findViewById(R.id.duedate);
        invoicetotxt = findViewById(R.id.invoicetotxt);
        invoiceto = findViewById(R.id.invoiceto);
        itemstxt = findViewById(R.id.itemstxt);
        subtotaltxt = findViewById(R.id.subtotaltxt);
        subtotal = findViewById(R.id.subtotal);
        discounttxt = findViewById(R.id.discounttxt);
        discount = findViewById(R.id.discount);
        taxtxt = findViewById(R.id.taxtxt);
        tax = findViewById(R.id.tax);
        additem = findViewById(R.id.additem);
        createinvoice = findViewById(R.id.createinvoice);
        options = findViewById(R.id.options);
        productsRecycler = findViewById(R.id.productsRecycler);
    }

    private void setFonts(){
        invoicenumtxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        invoicenum.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        duedatetxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        duedate.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        invoicetotxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        invoiceto.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        itemstxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Medium.otf"));
        subtotaltxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        subtotal.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        discounttxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        discount.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        taxtxt.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        tax.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Regular.otf"));
        additem.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        createinvoice.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/Ubuntu-Medium.ttf"));
    }

    private void setListeners(){

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                names.add("Product "+names.size()+1);
                quantity.add(String.valueOf(quantity.size()+1));
                price.add("$ 150"+price.size()+1);

                products_adapter = new Products_Adapter(Create_Invoice_Activity.this,names,quantity,price);
                productsRecycler.setAdapter(products_adapter);
                products_adapter.notifyDataSetChanged();
                productsRecycler.scrollToPosition(productsRecycler.getAdapter().getItemCount()-1);

            }
        });


        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
            }
        });

        createinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
            }
        });
    }
}






