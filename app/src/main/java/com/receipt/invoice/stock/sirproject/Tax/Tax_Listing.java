package com.receipt.invoice.stock.sirproject.Tax;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.receipt.invoice.stock.sirproject.Adapter.Tax_Listing_Adapter;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Model.Company_list;
import com.receipt.invoice.stock.sirproject.Model.Tax_list;
import com.receipt.invoice.stock.sirproject.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Tax_Listing extends android.support.v4.app.Fragment {

    public Tax_Listing() {
        // Required empty public constructor
    }

    RecyclerView recyclertax;
    ArrayList<Tax_list> list=new ArrayList<>();


    Tax_Listing_Adapter tax_listing_adapter;
    EditText search;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tax__listing, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        avi = view.findViewById(R.id.avi);
        avibackground = view.findViewById(R.id.avibackground);
        recyclertax = view.findViewById(R.id.recyclertax);
        search = view.findViewById(R.id.search);
        search.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/AzoSans-Light.otf"));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (list.size()>0) {
                    filter(s.toString());
                }
            }
        });

        taxget();
        tax_listing_adapter = new Tax_Listing_Adapter(getContext(),list);
        recyclertax.setAdapter(tax_listing_adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclertax.setLayoutManager(layoutManager);
        recyclertax.setHasFixedSize(true);
        tax_listing_adapter.notifyDataSetChanged();


        return view;
    }
    void filter(String text){
        ArrayList<Tax_list> temp = new ArrayList();
        for(Tax_list  d: list){
            if(d.getTax_name().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        tax_listing_adapter.updateList(temp);
    }

    public void taxget()
    {
        avi.smoothToShow();
        avibackground.setVisibility(View.VISIBLE);
        String token = Constant.GetSharedPreferences(getActivity(),Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL+"tax/listing", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody);
                Log.e("responsetax",response);
                avi.smoothToHide();
                avibackground.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray tax = data.getJSONArray("tax");
                        if (tax.length()>0) {
                            for (int i = 0; i < tax.length(); i++) {
                                JSONObject item = tax.getJSONObject(i);

                                String tax_id = item.getString("tax_id");
                                String tax_name = item.getString("name");
                                String tax_rate = item.getString("rate");
                                String company_id = item.getString("company_id");
                                String company_name = item.getString("company_name");

                                Tax_list tax_list = new Tax_list();
                                tax_list.setTax_id(tax_id);
                                tax_list.setCompanyid(company_id);
                                tax_list.setTax_name(tax_name);
                                tax_list.setTax_rate(tax_rate);
                                tax_list.setCompany_name(company_name);

                                list.add(tax_list);
                            }
                        }
                        else {
                            Constant.ErrorToast(getActivity(),jsonObject.getString("Tax Not Found"));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(responseBody!=null){
                    String response = new String(responseBody);
                    Log.e("responsetaxF",response);

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
