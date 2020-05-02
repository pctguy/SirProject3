package com.receipt.invoice.stock.sirproject.Company;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isapanah.awesomespinner.AwesomeSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signup_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Verification_Code_Activity;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Company extends Fragment {


    public Add_Company() {
        // Required empty public constructor
    }
    AwesomeSpinner defaultcurrency;
    ArrayList<String> currencies = new ArrayList<>();
    ArrayList<String> currencies_id = new ArrayList<>();
    String selected_currency="";
    RoundedImageView uploadimage;
    File fileimage;
    TextView heading, description, imagedescription, haveoneormore, list;
    EditText name, email, phone, website, CompanyAddress;
    Button save, addwarehouse;

    String edname="",edemail="",edphone="",edwebsite="",edCompanyAddress="";
    String company_id="";
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    TextView terms,termsdescprice1,termdescription1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add__company, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        FindByIds(view);
        DefaultCurrencies();
        setListeners();
        setFonts();
        return view;
    }
    private void FindByIds(View view) {
        defaultcurrency = view.findViewById(R.id.defaultcurrency);
        uploadimage = view.findViewById(R.id.uploadimage);
        heading = view.findViewById(R.id.heading);
        description = view.findViewById(R.id.description);
        imagedescription = view.findViewById(R.id.imagedescription);
        haveoneormore = view.findViewById(R.id.haveoneormore);
        list = view.findViewById(R.id.list);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        website = view.findViewById(R.id.website);
        CompanyAddress = view.findViewById(R.id.CompanyAddress);
        save = view.findViewById(R.id.save);
        addwarehouse = view.findViewById(R.id.addwarehouse);
        avi = view.findViewById(R.id.avi);
        avibackground = view.findViewById(R.id.avibackground);
        terms = view.findViewById(R.id.terms);
        termsdescprice1 = view.findViewById(R.id.termsdescprice1);
        termdescription1 = view.findViewById(R.id.termdescription1);
    }
    private void setListeners() {
        uploadimage.setOnClickListener(new View.OnClickListener() {
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
                                    uploadimage.setImageURI(r.getUri());
                                    Log.e("pathGallery",r.getPath());
                                }
                                else if (String.valueOf(r.getUri()).contains(".jpg") || String.valueOf(r.getUri()).contains(".jpeg") ||
                                        String.valueOf(r.getUri()).contains(".png")){
                                    //camera
                                    fileimage = new File(String.valueOf(r.getUri()));
                                    uploadimage.setImageURI(r.getUri());
                                    Log.e("pathCam",String.valueOf(r.getUri()));
                                }
                                uploadimage.setCornerRadius(200);
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
        defaultcurrency.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                defaultcurrency.setSelected(true);
                selected_currency = currencies_id.get(position);
                Log.e("selected_currecy",selected_currency);
            }
        });
        addwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getActivity(),Add_Warehouse_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("company_id",company_id);
                startActivity(intent);*/
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcompany();
            }
        });
    }
    private void setFonts() {
        list.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        haveoneormore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        imagedescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        heading.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Bold.otf"));
        CompanyAddress.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        website.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        phone.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        email.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        addwarehouse.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        save.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        termsdescprice1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Bold.ttf"));
        termdescription1.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));
        terms.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Light.ttf"));



    }
    private void DefaultCurrencies(){
        String token = Constant.GetSharedPreferences(getContext(),Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL + "currency/all", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("currencyResp",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray currency = data.getJSONArray("currencies");
                        if (currency.length()>0){
                            for (int i=0; i<currency.length(); i++){
                                JSONObject item = currency.getJSONObject(i);
                                String currency_id = item.getString("currency_id");
                                String code = item.getString("code");
                                currencies_id.add(currency_id);
                                currencies.add(code);
                                ArrayAdapter<String> defaultcurrencyadap = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, currencies);
                                defaultcurrency.setAdapter(defaultcurrencyadap);
                                defaultcurrency.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
                                defaultcurrency.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(responseBody!=null) {
                    String response = new String(responseBody);
                    Log.e("currencyRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false")){
                            //Constant.ErrorToast(Company_Setup_Activity.this,jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    public void addcompany()
    {
        edname = name.getText().toString();
        edemail = email.getText().toString();
        edphone = phone.getText().toString();
        edwebsite = website.getText().toString();
        edCompanyAddress = CompanyAddress.getText().toString();


        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Field is required");
            name.requestFocus();
        }
        else if (selected_currency.equals(""))
        {
            Constant.ErrorToast(getActivity(),"Please select currency");
        }
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
            CompanyAddress.setError("Field is required");
            CompanyAddress.requestFocus();
        }
        else {
            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);
            RequestParams params = new RequestParams();
            params.add("name",edname);
            params.add("currency_id",selected_currency);
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
            client.post(Constant.BASE_URL + "company/add", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);
                    Log.e("responsecompany",response);
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONObject company = data.getJSONObject("company");
                            company_id = company.getString("company_id");

                            Constant.SuccessToast(getActivity(),"Company Created");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getContext(),Companies_Activity.class);
                                    intent.putExtra("add","add");
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
                        Log.e("responsecompanyF",response);

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
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);

                }
            });
        }
    }
}
