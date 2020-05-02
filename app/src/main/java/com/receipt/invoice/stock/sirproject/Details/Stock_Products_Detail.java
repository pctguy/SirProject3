package com.receipt.invoice.stock.sirproject.Details;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.receipt.invoice.stock.sirproject.Adapter.Stock_Products_Details_Adapter;
import com.receipt.invoice.stock.sirproject.Adapter.Warehouses_Listing_Adapter;
import com.receipt.invoice.stock.sirproject.Company.Companies_Activity;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Model.Stock_Products;
import com.receipt.invoice.stock.sirproject.Model.Warehouse_list;
import com.receipt.invoice.stock.sirproject.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Stock_Products_Detail extends AppCompatActivity {

    String product_id;
    RecyclerView warehouse;
    ArrayList<Stock_Products> list=new ArrayList<>();


    Stock_Products_Details_Adapter stock_products_details_adapter;
    EditText search;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String company_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock__products__detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Constant.toolbar(Stock_Products_Detail.this,"Warehouse (s)");
        Constant.bottomNav(Stock_Products_Detail.this,0);
        final SharedPreferences pref =getSharedPreferences(Constant.PREF_BASE, Context.MODE_PRIVATE);
        company_id = pref.getString(Constant.COMPANY_ID,"");
        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        warehouse = findViewById(R.id.warehouse);
        search = findViewById(R.id.search);
        search.setTypeface(Typeface.createFromAsset(getAssets(),"Fonts/AzoSans-Light.otf"));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                filter(s.toString());
            }
        });


        if (getIntent().hasExtra("product_id"))
        {
            product_id = getIntent().getStringExtra("product_id");
            product_detail1(product_id);
        }


    }
    public void product_detail1(String pid)
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        params.add("product_id",pid);
        String token = Constant.GetSharedPreferences(Stock_Products_Detail.this,Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL + "product/detail", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsecompd", response);

                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String company_image_path = data.getString("company_image_path");
                        JSONObject product = data.getJSONObject("product");
                        String company_logo = product.getString("company_logo");

                        JSONArray product_quantity = data.getJSONArray("product_quantity");
                        if (product_quantity.length()>0){

                            for (int i = 0; i<product_quantity.length(); i++){
                                JSONObject item = product_quantity.getJSONObject(i);
                                String total_quantity = item.getString("toal_quantity");
                                String warehousen = item.getString("warehouse");

                                Stock_Products stock_products = new Stock_Products();
                                stock_products.setTotal_quantity(total_quantity);
                                stock_products.setWarehouse(warehousen);
                                stock_products.setImage(company_logo);
                                stock_products.setImagepath(company_image_path);

                                list.add(stock_products);

                                stock_products_details_adapter = new Stock_Products_Details_Adapter(Stock_Products_Detail.this,list);
                                warehouse.setAdapter(stock_products_details_adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Stock_Products_Detail.this, LinearLayoutManager.VERTICAL, false);
                                warehouse.setLayoutManager(layoutManager);
                                warehouse.setHasFixedSize(true);
                                stock_products_details_adapter.notifyDataSetChanged();

                            }

                        }
                        else {
                            Constant.ErrorToast(Stock_Products_Detail.this,"No Warehouse Found");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                if(responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("responsecompdF",response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("false"))
                        {

                            //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            Constant.ErrorToast(Stock_Products_Detail.this,jsonObject.getString("message"));


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Constant.ErrorToast(Stock_Products_Detail.this,"Something wen wrong, try again!");
                }



            }
        });




    }

    void filter(String text){
        ArrayList<Stock_Products> temp = new ArrayList();
        for(Stock_Products  d: list){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getWarehouse().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        stock_products_details_adapter.updateList(temp);
    }
}
