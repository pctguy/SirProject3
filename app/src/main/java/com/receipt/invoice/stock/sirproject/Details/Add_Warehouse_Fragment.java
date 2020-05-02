package com.receipt.invoice.stock.sirproject.Details;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Warehouse_Fragment extends Fragment {


    public Add_Warehouse_Fragment() {
        // Required empty public constructor
    }

    EditText edwarehousename,edwarehouseaddress;
    Button add;
    TextView addwarehouse;
    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    String company_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =   inflater.inflate(R.layout.fragment_add__warehouse_, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        avi = v.findViewById(R.id.avi);
        avibackground = v.findViewById(R.id.avibackground);
        FindByIds(v);
        setFonts();
        setListeners();

        final SharedPreferences pref =getActivity().getSharedPreferences(Constant.PREF_BASE, Context.MODE_PRIVATE);
        company_id = pref.getString(Constant.COMPANY_ID,"");


        return v;
    }
    private void FindByIds(View v){
        addwarehouse = v.findViewById(R.id.addwarehouse);
        edwarehousename = v.findViewById(R.id.edwarehousename);
        edwarehouseaddress = v.findViewById(R.id.edwarehouseaddress);
        add = v.findViewById(R.id.add);
        }

    private void setFonts(){

        addwarehouse.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        edwarehousename.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        edwarehouseaddress.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Regular.ttf"));
        add.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/Ubuntu-Medium.ttf"));

    }

    private void setListeners(){


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity(),"Coming Soon",Toast.LENGTH_SHORT).show();
                //API and Intent to Warehouses Listing
                warehouseadd();
            }
        });

    }

    public void warehouseadd()
    {
        if (TextUtils.isEmpty(edwarehousename.getText())) {
            edwarehousename.setError("Field is required");
            edwarehousename.requestFocus();
        }
        else if (TextUtils.isEmpty(edwarehouseaddress.getText())) {
            edwarehouseaddress.setError("Field is required");
            edwarehouseaddress.requestFocus();
        }

        else
        {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);

            final String warehouse_name = edwarehousename.getText().toString();
            String warehouse_address = edwarehouseaddress.getText().toString();


            RequestParams params = new RequestParams();

            params.add("company_id",company_id);
            params.add("name",warehouse_name);
            params.add("address",warehouse_address);
            String token = Constant.GetSharedPreferences(getActivity(),Constant.ACCESS_TOKEN);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Access-Token",token);
            client.post(Constant.BASE_URL + "warehouse/add", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String response = new String(responseBody);

                    Log.e("responsewarehouse",response);

                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("true"))
                        {
                            Constant.SuccessToast(getActivity(),jsonObject.getString("message"));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getContext(),Company_Details_Activity.class);
                                    intent.putExtra("warehouse","warehouse");
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
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    if(responseBody!=null){
                        String response = new String(responseBody);
                        Log.e("responsewarehouse",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");
                            if (status.equals("false"))
                            {

                                //Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
