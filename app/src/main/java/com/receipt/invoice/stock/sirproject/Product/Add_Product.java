package com.receipt.invoice.stock.sirproject.Product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Adapter.Product_Listing_Adapter;
import com.receipt.invoice.stock.sirproject.Adapter.Select_Warehouse_Adapter;
import com.receipt.invoice.stock.sirproject.Company.Companies_Activity;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.R;
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

import static android.content.Context.MODE_PRIVATE;

public class Add_Product extends android.support.v4.app.Fragment implements Select_Warehouse_Adapter.Callback{


    public Add_Product() {
        // Required empty public constructor
    }

    RoundedImageView uploadimage;
    TextView imagedescription,selectwarehouse,taxable,select;
    EditText productname,productprice,productdescription,reorderlevel,othercategory;
    Button addproduct;
    RadioButton ryes,rno;
    RadioGroup radiogroup;
    AwesomeSpinner productcategory,measurementunit,selectcompany;
    ArrayList<String> companies = new ArrayList<>();
    File fileimage;
    final int PICK_CONTACT=1;
    final int PERMISSION_REQUEST_CONTACT=10;

    RecyclerView recyclerwarehouse;
    ArrayList<String> warehousename=new ArrayList<>();

    Select_Warehouse_Adapter select_Warehouse_Adapter;
    Dialog mybuilder;

    EditText weight;

    ArrayList<String> measurementunits = new ArrayList<>();
    ArrayList<String> measurementIds = new ArrayList<>();
    private AVLoadingIndicatorView avi;
    ImageView avibackground;

    String selectedCategoryId="";
    String selectedMeasuremetnId="";
    String selectedCompanyId="";
    String selectedTaxable="";

    ArrayList<String> cnames=new ArrayList<>();
    ArrayList<String> cids=new ArrayList<>();

    ArrayList<String> catnames=new ArrayList<>();
    ArrayList<String> catids=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add__product, container, false);

        FindByIds(view);
        setFonts();
        setListeners();

        MeasurementUnits();
        companyget();
        categoriesget();

        productcategory.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
        productcategory.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));
        measurementunit.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
        measurementunit.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));
        selectcompany.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
        selectcompany.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));

        return view;
    }

    private void FindByIds(View view){
        uploadimage = view.findViewById(R.id.uploadimage);
        imagedescription = view.findViewById(R.id.imagedescription);
        selectwarehouse = view.findViewById(R.id.selectwarehouse);
        taxable = view.findViewById(R.id.taxable);
        productname = view.findViewById(R.id.productname);
        productprice = view.findViewById(R.id.productprice);
        productdescription = view.findViewById(R.id.productdescription);
        addproduct = view.findViewById(R.id.addprodduct);
        ryes = view.findViewById(R.id.ryes);
        rno = view.findViewById(R.id.rno);
        radiogroup = view.findViewById(R.id.radiogroup);
        productcategory = view.findViewById(R.id.productcategory);
        measurementunit = view.findViewById(R.id.measurementunit);
        selectcompany = view.findViewById(R.id.selectcompany);
        weight = view.findViewById(R.id.weight);
        avi = view.findViewById(R.id.avi);
        avibackground = view.findViewById(R.id.avibackground);
        reorderlevel = view.findViewById(R.id.reorderlevel);
        othercategory = view.findViewById(R.id.othercategory);
    }
    private void setFonts() {

        imagedescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        productname.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        reorderlevel.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        othercategory.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        weight.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        productprice.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        productdescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        selectwarehouse.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Light.otf"));
        taxable.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Bold.otf"));
        ryes.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Medium.otf"));
        rno.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Medium.otf"));
        addproduct.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Medium.ttf"));

    }
    private void setListeners()
    {

        selectwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WareHouseDialog();
            }
        });

        selectcompany.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                selectedCompanyId = cids.get(position);
                Log.e("selectedCompany",selectedCompanyId);
            }
        });

        productcategory.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                selectedCategoryId = catids.get(position);
                Log.e("selectedCategory",selectedCategoryId);

            }
        });

        measurementunit.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                measurementunit.setSelected(true);
                //weight.animate().setDuration(1000).alpha(1.0f);
                selectedMeasuremetnId = measurementIds.get(position);
                Log.e("selectedMeasuremetnId",selectedMeasuremetnId);
            }
        });

        ryes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    selectedTaxable="1";
                }
            }
        });

        rno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    selectedTaxable="0";
                }
            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProduct();
            }
        });



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
    }

    private void MeasurementUnits(){
        RequestParams params = new RequestParams();
        params.add("weight_class","PRODUCT");
        String token = Constant.GetSharedPreferences(getContext(),Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL + "product/getMeasurementUnit", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("munitsResp",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray unit = data.getJSONArray("unit");
                        if (unit.length()>0){
                            for (int i=0; i<unit.length(); i++){
                                JSONObject item = unit.getJSONObject(i);
                                String weight_class_id = item.getString("weight_class_id");
                                String units = item.getString("title");
                                measurementIds.add(weight_class_id);
                                measurementunits.add(units);
                                ArrayAdapter<String> munitsadp = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, measurementunits);
                                measurementunit.setAdapter(munitsadp);
                                measurementunit.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
                                measurementunit.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));
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
                    Log.e("munitsRespF",response);
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

    public void companyget()
    {

        final SharedPreferences preferences = getActivity().getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
        String token = Constant.GetSharedPreferences(getActivity(),Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL+"company/listing", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("responsecompany",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray company = data.getJSONArray("company");
                        if (company.length()>0) {
                            for (int i = 0; i < company.length(); i++) {
                                JSONObject item = company.getJSONObject(i);
                                String company_id = item.getString("company_id");
                                String company_name = item.getString("name");

                                cnames.add(company_name);
                                cids.add(company_id);

                                ArrayAdapter<String> namesadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,cnames);
                                selectcompany.setAdapter(namesadapter);

                            }
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
                    Log.e("responsecompanyF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false"))
                        {
                            //Constant.ErrorToast(Home_Activity.this,jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void categoriesget()
    {

        String token = Constant.GetSharedPreferences(getActivity(),Constant.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Access-Token",token);
        client.post(Constant.BASE_URL+"category/listing", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Log.e("categoriesResp",response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("true"))
                    {
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONArray category = data.getJSONArray("category");
                        if (category.length()>0) {
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject item = category.getJSONObject(i);
                                String category_id = item.getString("category_id");
                                String name = item.getString("name");

                                catids.add(category_id);
                                catnames.add(name);

                                ArrayAdapter<String> catadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,catnames);
                                productcategory.setAdapter(catadapter);

                            }
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
                    Log.e("categoriesRespF",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("false"))
                        {
                            //Constant.ErrorToast(Home_Activity.this,jsonObject.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void AddProduct(){

        String pname = productname.getText().toString();
        String price = productprice.getText().toString();
        //String pweight = weight.getText().toString();
        String pdesc = productdescription.getText().toString();
        String reorder = reorderlevel.getText().toString();
        String othercat = othercategory.getText().toString();

        if (pname.isEmpty()){
            productname.setError("Required");
            productname.requestFocus();
        }
        else if (price.isEmpty()){
            productprice.setError("Required");
            productprice.requestFocus();
        }
        else if (pdesc.isEmpty()){
            productdescription.setError("Required");
            productdescription.requestFocus();
        }
        else if (selectedMeasuremetnId.equals("")){
            Constant.ErrorToast(getActivity(),"Measurement unit is required");
        }
        else if (selectedCompanyId.equals("")){
            Constant.ErrorToast(getActivity(),"Company is required");
        }
        else if (selectedTaxable.equals("")){
            Constant.ErrorToast(getActivity(),"Tax information is required");
        }
        else if (selectedCategoryId.equals("") && othercat.isEmpty()){
            Constant.ErrorToast(getActivity(),"Product category is required");
        }
        else {

            avi.smoothToShow();
            avibackground.setVisibility(View.VISIBLE);
            RequestParams params = new RequestParams();
            params.add("name",pname);
            params.add("price",price);
            params.add("minimum",reorder);
            params.add("weight_class_id",selectedMeasuremetnId);
           // params.add("weight",pweight);
            params.add("description",pdesc);
            params.add("company_id",selectedCompanyId);
            params.add("is_taxable",selectedTaxable);
            params.add("product_type","PRODUCT");

            if (othercat.isEmpty()){
                params.add("category_id",selectedCategoryId);
            }
            else if (selectedCategoryId.equals("")){
                params.add("category_name",othercat);
            }
            else {
                params.add("category_id",selectedCategoryId);
            }

            if (fileimage!=null){
                try {
                    params.put("image",fileimage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Log.e("paramss", String.valueOf(params));

            String token = Constant.GetSharedPreferences(getContext(),Constant.ACCESS_TOKEN);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Access-Token",token);
            client.post(Constant.BASE_URL + "product/add", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    avi.smoothToHide();
                    avibackground.setVisibility(View.GONE);
                    String response = new String(responseBody);
                    Log.e("addproductResp",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("true")){

                            Constant.SuccessToast(getActivity(),"Product Added");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getContext(),Product_Activity.class);
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
                    if(responseBody!=null) {
                        String response = new String(responseBody);
                        Log.e("addproductRespF",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("false")){

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
    @Override
    public void onWarehouseSelected(int count, ArrayList<String> warehouseList) {
        Log.e("counts", String.valueOf(count));
        mybuilder.dismiss();
        if (count!=0){
            selectwarehouse.setText(String.valueOf(count)+" warehouse selected");
        }
        else {
            selectwarehouse.setText("Select Warehouse (s)");

        }
    }
}

