package com.receipt.invoice.stock.sirproject.Details;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
/*
import android.support.v4.app.Fragment;
*/
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Company.Companies_Activity;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class Company_Details_Fragment extends android.support.v4.app.Fragment {


    public Company_Details_Fragment() {
        // Required empty public constructor
    }


    EditText name,email,phone,website,address,description;
    TextView nametxt,emailtxt,phonetxt,webtxt,addresstxt;
    ScrollView sv;
    RoundedImageView image;
    private AVLoadingIndicatorView avi;
    ImageView avibackground,changepic,editbtn;
    TextView save,cancel;
    File fileimage;


    String company_name,company_logo,company_email,company_phone,comapny_website,comapny_address,company_id,currencyid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SharedPreferences pref =getActivity().getSharedPreferences(Constant.PREF_BASE, Context.MODE_PRIVATE);
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_company__details_, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        avi = v.findViewById(R.id.avi);
        avibackground = v.findViewById(R.id.avibackground);

        FindByIds(v);
        setFonts();
        setListeners();

        company_name = pref.getString(Constant.COMPANY_NAME,"");
        company_logo = pref.getString(Constant.COMPANY_LOGO,"");
        company_email = pref.getString(Constant.COMPANY_EMAIL,"");
        company_phone = pref.getString(Constant.COMPANY_PHONE,"");
        comapny_website = pref.getString(Constant.COMPANY_WEB,"");
        comapny_address = pref.getString(Constant.COMPANY_ADDRESS,"");
        company_id = pref.getString(Constant.COMPANY_ID,"");
        currencyid = pref.getString(Constant.CURRENCY_ID,"");


        name.setText(company_name);
        email.setText(company_email);
        phone.setText(company_phone);
        website.setText(comapny_website);
        address.setText(comapny_address);
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.placeholder_big);
        Glide.with(getActivity())
                .load(company_logo)
                .apply(options)
                .into(image);



        return v;
    }

    private void FindByIds(View v){
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        phone = v.findViewById(R.id.phone);
        website = v.findViewById(R.id.website);
        address = v.findViewById(R.id.address);
        description = v.findViewById(R.id.description);
        sv = v.findViewById(R.id.sv);
        image = v.findViewById(R.id.image);
        changepic = v.findViewById(R.id.changepic);
        nametxt = v.findViewById(R.id.nametxt);
        emailtxt = v.findViewById(R.id.emailtxt);
        phonetxt = v.findViewById(R.id.phonetxt);
        webtxt = v.findViewById(R.id.webtxt);
        addresstxt = v.findViewById(R.id.addresstxt);
        editbtn = v.findViewById(R.id.editbtn);
        save = v.findViewById(R.id.save);
        cancel = v.findViewById(R.id.cancel);
    }

    private void setFonts(){

        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Medium.otf"));
        phone.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));
        email.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));
        website.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));
        address.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));
        description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));
        nametxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        emailtxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        phonetxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        webtxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        addresstxt.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        cancel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/AzoSans-Regular.otf"));
        save.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/AzoSans-Regular.otf"));

    }

    private void setListeners(){

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCompany();
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                changepic.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                email.setEnabled(true);
                phone.setEnabled(true);
                website.setEnabled(true);
                address.setEnabled(true);
                editbtn.setVisibility(View.GONE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                changepic.setVisibility(View.GONE);
                name.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                website.setEnabled(false);
                address.setEnabled(false);
                editbtn.setVisibility(View.VISIBLE);

                //if cancel then previous data restore
                name.setText(company_name);
                email.setText(company_email);
                phone.setText(company_phone);
                website.setText(comapny_website);
                address.setText(comapny_address);
                RequestOptions options = new RequestOptions();
                options.centerCrop();
                options.placeholder(R.drawable.placeholder_big);
                Glide.with(getActivity())
                        .load(company_logo)
                        .apply(options)
                        .into(image);

            }
        });


        changepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickSetup setup = new PickSetup().setSystemDialog(true);

                PickImageDialog.build(setup)
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                if (r.getPath().contains(".jpg") || r.getPath().contains(".jpeg") || r.getPath().contains(".png")){
                                    //gallery
                                    fileimage = new File(r.getPath());
                                    image.setImageURI(r.getUri());
                                    Log.e("pathGallery",r.getPath());
                                }
                                else if (String.valueOf(r.getUri()).contains(".jpg") || String.valueOf(r.getUri()).contains(".jpeg") ||
                                        String.valueOf(r.getUri()).contains(".png")){
                                    //camera
                                    fileimage = new File(String.valueOf(r.getUri()));
                                    image.setImageURI(r.getUri());
                                    Log.e("pathCam",String.valueOf(r.getUri()));
                                }
                                //uploadimage.setCornerRadius(200);
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                // Toast.makeText(getContext(),r.getError().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }).show(getFragmentManager());
            }
        });
    }

    private void EditCompany(){

        String edname = name.getText().toString();
        String edemail = email.getText().toString();
        String edphone = phone.getText().toString();
        String edwebsite = website.getText().toString();
        String edCompanyAddress = address.getText().toString();


        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Field is required");
            name.requestFocus();
        }
       /* else if (selected_currency.equals(""))
        {
            Constant.ErrorToast(Company_Edit.this,"Please select currency");
        }*/
        else if (edemail.isEmpty()){
            email.setError("Field is required");
            email.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(edemail).matches()){
            email.setError("Invalid email");
            email.requestFocus();
        }
        else if (edphone.isEmpty()){
            phone.setError("Field is required");
            phone.requestFocus();
        }
        else if (edwebsite.isEmpty()){
            website.setError("Field is required");
            website.requestFocus();
        }
        else if (edCompanyAddress.isEmpty()){
            address.setError("Field is required");
            address.requestFocus();
        }
        else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            RequestParams params = new RequestParams();
            params.add("company_id",company_id);
            params.add("name",edname);
            params.add("currency_id",currencyid);
            params.add("phone_number",edphone);
            params.add("email",edemail);
            params.add("website",edwebsite);
            params.add("address",edCompanyAddress);
            if (fileimage!=null){
                try {
                    params.put("image",fileimage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            String token = Constant.GetSharedPreferences(getActivity(),Constant.ACCESS_TOKEN);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Access-Token",token);
            client.post(Constant.BASE_URL + "company/update", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsecompanyedit",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            Constant.SuccessToast(getActivity(),"Company updated");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getActivity(),Companies_Activity.class);
                                    //intent.putExtra("add","add");
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
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    if(responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("responsecompanyeditF",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            if (status.equals("false"))
                            {
                                Constant.ErrorToast(getActivity(),jsonObject.getString("message"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Constant.ErrorToast(getActivity(),"Something wen wrong, try again!");
                    }


                }
            });

        }

    }
}
