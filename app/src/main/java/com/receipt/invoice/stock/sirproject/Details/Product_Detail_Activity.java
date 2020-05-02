package com.receipt.invoice.stock.sirproject.Details;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signin_Activity;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Product_Detail_Activity extends AppCompatActivity {

    TextView name,price,category,measurement,description,taxable;
    RoundedImageView image;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail_);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Constant.toolbar(Product_Detail_Activity.this,"Details");
        Constant.bottomNav(Product_Detail_Activity.this,0);

        avi = findViewById(R.id.avi);
        avibackground = findViewById(R.id.avibackground);
        FindByIds();
        setFonts();

        if (getIntent().hasExtra("product_id"))
        {
            product_id = getIntent().getStringExtra("product_id");
            product_detail1();
        }



    }
    private void FindByIds(){
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        category = findViewById(R.id.category);
        measurement = findViewById(R.id.measurement);
        description = findViewById(R.id.description);
        taxable = findViewById(R.id.taxable);
    }
    private void setFonts(){

        name.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Medium.otf"));
        price.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Medium.otf"));
        category.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));
        measurement.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));
        taxable.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));
        description.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/AzoSans-Regular.otf"));

    }


    public void product_detail1()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams();
        params.add("product_id",product_id);
        String token = Constant.GetSharedPreferences(Product_Detail_Activity.this,Constant.ACCESS_TOKEN);
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
                        JSONObject product = data.getJSONObject("product");
                        String product_name = product.getString("name");
                        String p_image = product.getString("image");
                        String p_price = product.getString("price");
                        String currency_code = product.getString("currency_code");
                        String p_category = product.getString("category");
                        String measurement_unit = product.getString("measurement_unit");
                        String desc = product.getString("description");
                        String is_taxable = product.getString("is_taxable");
                        String product_image_path = data.getString("product_image_path");

                        if (product_name.equals("") || product_name.equals("null")) {

                            name.setText("N/A");
                        } else {
                            name.setText(product_name);
                        }

                        if (p_price.equals("") || p_price.equals("null")) {

                            price.setText("N/A");
                        } else {
                            price.setText(p_price +" "+currency_code+" / Per Unit");
                        }

                        if (p_category.equals("") || p_category.equals("null")) {

                            category.setText("N/A");
                        } else {
                            category.setText(p_category);
                        }

                        if (desc.equals("") || desc.equals("null")) {

                            description.setText("N/A");
                        } else {
                            description.setText(desc);
                        }

                        if (measurement_unit.equals("") || measurement_unit.equals("null")) {

                            measurement.setText("N/A");
                        } else {
                            measurement.setText("Measurement Unit: "+measurement_unit);
                        }

                        if (is_taxable.equals("") || measurement_unit.equals("null")) {

                            taxable.setText("N/A");
                        }
                        else if (is_taxable.equals("0")) {
                            taxable.setText("Taxable: No");
                        } else if (is_taxable.equals("1")) {
                            taxable.setText("Taxable: Yes");
                        }

                        RequestOptions options = new RequestOptions();
                        options.centerCrop();
                        options.placeholder(R.drawable.placeholder_big);
                        Glide.with(Product_Detail_Activity.this)
                                .load(product_image_path+p_image)
                                .apply(options)
                                .into(image);

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
                            Constant.ErrorToast(Product_Detail_Activity.this,jsonObject.getString("message"));


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    Constant.ErrorToast(Product_Detail_Activity.this,"Something wen wrong, try again!");
                }



            }
        });




    }


}
