package com.receipt.invoice.stock.sirproject.Vendor;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.isapanah.awesomespinner.AwesomeSpinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Customer.Customer_Activity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Vendor extends Fragment {


    public Add_Vendor() {
        // Required empty public constructor
    }

    RoundedImageView uploadimage;
    TextView imagedescription,contacts,type;
    EditText name,email,contactperson,phone,mobile,website,CompanyAddress;
    Button add;
    File fileimage;
    final int PICK_CONTACT=1;
    final int PERMISSION_REQUEST_CONTACT=10;

    String contactname,contactnumber;

    private AVLoadingIndicatorView avi;
    ImageView avibackground;
    AwesomeSpinner selectcompany;
    String selectedCompanyId="";

    RadioButton individual,company;
    String customertype="company";

    ArrayList<String> cnames=new ArrayList<>();
    ArrayList<String> cids=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add__vendor, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        FindByIds(view);
        setFonts();
        setListeners();

        selectcompany.setDownArrowTintColor(getResources().getColor(R.color.lightpurple));
        selectcompany.setSelectedItemHintColor(getResources().getColor(R.color.lightpurple));

        companyget();

        return view;
    }
    private void FindByIds(View view){
        uploadimage = view.findViewById(R.id.uploadimage);
        imagedescription = view.findViewById(R.id.imagedescription);
        contacts = view.findViewById(R.id.contacts);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        contactperson = view.findViewById(R.id.contactperson);
        phone = view.findViewById(R.id.phone);
        mobile = view.findViewById(R.id.mobile);
        website = view.findViewById(R.id.website);
        CompanyAddress = view.findViewById(R.id.CompanyAddress);
        add = view.findViewById(R.id.add);
        avi = view.findViewById(R.id.avi);
        avibackground = view.findViewById(R.id.avibackground);
        selectcompany = view.findViewById(R.id.selectcompany);
        individual = view.findViewById(R.id.individual);
        company = view.findViewById(R.id.company);
        type = view.findViewById(R.id.type);

    }
    private void setFonts() {
        type.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        individual.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        company.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        imagedescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        CompanyAddress.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        website.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        phone.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        email.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        contactperson.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        mobile.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        add.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        contacts.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Regular.otf"));

    }
    private void setListeners(){

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForContactPermission();
            }
        });

        selectcompany.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                selectedCompanyId = cids.get(position);
                Log.e("selectedCompany",selectedCompanyId);
            }
        });

        company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    name.animate().setDuration(1500).alpha(1.0f);
                    name.setEnabled(true);
                    customertype="company";
                }

            }
        });
        individual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    name.animate().setDuration(1500).alpha(0.0f);
                    name.setEnabled(false);
                    customertype="individual";

                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVendor();
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
    private void AddVendor(){

        String companyname = name.getText().toString();
        String cemail = email.getText().toString();
        String cperson = contactperson.getText().toString();
        String cphone = phone.getText().toString();
        String cmobile= mobile.getText().toString();
        String cwebsite = website.getText().toString();
        String caddress = CompanyAddress.getText().toString();

        if (customertype.equals("company")){
            if (companyname.isEmpty()){
                name.setError("Required");
                name.requestFocus();
            }
            else if (!cemail.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(cemail).matches()){
                email.setError("Invalid email");
                email.requestFocus();
            }

/*
            else if (cemail.isEmpty()){
                email.setError("Required");
                email.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(cemail).matches()){
                email.setError("Invalid email");
                email.requestFocus();
            }
            else if (cperson.isEmpty()){
                contactperson.setError("Required");
                contactperson.requestFocus();
            }
            else if (cphone.isEmpty()){
                phone.setError("Required");
                phone.requestFocus();
            }
            else if (cmobile.isEmpty()){
                mobile.setError("Required");
                mobile.requestFocus();
            }
            else if (cwebsite.isEmpty()){
                website.setError("Required");
                website.requestFocus();
            }
            else if (caddress.isEmpty()){
                CompanyAddress.setError("Required");
                CompanyAddress.requestFocus();
            }
*/
            else if (selectedCompanyId.equals("")){
                Constant.ErrorToast(getActivity(),"Select Company");
            }
            else{
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);

                RequestParams params = new RequestParams();
                params.add("supplier_name",companyname);
                params.add("company_id",selectedCompanyId);
                params.add("contact_name",cperson);
                params.add("email",cemail);
                params.add("address",caddress);
                params.add("phone_number",cphone);
                params.add("mobile_number",cmobile);
                params.add("website",cwebsite);
                if (fileimage!=null){
                    try {
                        params.put("image",fileimage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                String token = Constant.GetSharedPreferences(getContext(),Constant.ACCESS_TOKEN);
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Access-Token",token);
                client.post(Constant.BASE_URL + "supplier/add", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        String response = new String(responseBody);
                        Log.e("addvendorResp",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("true")){
                                Constant.SuccessToast(getActivity(),"Supplier Added");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(),Vendor_Activity.class);
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
                            Log.e("addvendorRespF",response);

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
        else{

            if (cemail.isEmpty()){
                email.setError("Required");
                email.requestFocus();
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(cemail).matches()){
                email.setError("Invalid email");
                email.requestFocus();
            }
            else if (cperson.isEmpty()){
                contactperson.setError("Required");
                contactperson.requestFocus();
            }
            else if (cphone.isEmpty()){
                phone.setError("Required");
                phone.requestFocus();
            }
            else if (cmobile.isEmpty()){
                mobile.setError("Required");
                mobile.requestFocus();
            }
            else if (cwebsite.isEmpty()){
                website.setError("Required");
                website.requestFocus();
            }
            else if (caddress.isEmpty()){
                CompanyAddress.setError("Required");
                CompanyAddress.requestFocus();
            }
            else if (selectedCompanyId.equals("")){
                Constant.ErrorToast(getActivity(),"Select Company");
            }
            else{
                avi.smoothToShow();
                avibackground.setVisibility(View.VISIBLE);

                RequestParams params = new RequestParams();
                params.add("company_id",selectedCompanyId);
                params.add("contact_name",cperson);
                params.add("email",cemail);
                params.add("address",caddress);
                params.add("phone_number",cphone);
                params.add("mobile_number",cmobile);
                params.add("website",cwebsite);
                if (fileimage!=null){
                    try {
                        params.put("image",fileimage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                String token = Constant.GetSharedPreferences(getContext(),Constant.ACCESS_TOKEN);
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Access-Token",token);
                client.post(Constant.BASE_URL + "supplier/add", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        avi.smoothToHide();
                        avibackground.setVisibility(View.GONE);
                        String response = new String(responseBody);
                        Log.e("addvendorResp",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("true")){
                                Constant.SuccessToast(getActivity(),"Vendor Added");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(),Vendor_Activity.class);
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
                            Log.e("addvendorRespF",response);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c =  getActivity().managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {


                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();
                            contactnumber = phones.getString(phones.getColumnIndex("data1"));
                        }
                        contactname = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        name.setText(contactname);
                        contactperson.setText(contactname);
                        mobile.setText(contactnumber);

                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                }
                else {
                    Constant.ErrorToast(getActivity(),"Permission Denied for contacts");
                }
                return;
            }
        }
    }

    public void askForContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();


                } else {

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                }
            }else{
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        }
        else{
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }
}
