package com.receipt.invoice.stock.sirproject.User;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.isapanah.awesomespinner.AwesomeSpinner;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;

public class Add_User extends android.support.v4.app.Fragment {


    public Add_User() {
        // Required empty public constructor
    }



    TextView username,useremail;
    AwesomeSpinner userrole;
    TextView accessrights,txtterms,txtdescription,imagedescription;
    CheckBox chkinvoices,chkestimates,chkreceipts,chkpurchase,chkpayment,chkstock,chktax,chksubadmin;
    Button adduser;
    RoundedImageView uploadimage;
    File fileimage;
    final int PICK_CONTACT=1;
    final int PERMISSION_REQUEST_CONTACT=10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add__user, container, false);

        username = view.findViewById(R.id.username);
        useremail = view.findViewById(R.id.useremail);
        userrole = view.findViewById(R.id.userrole);
        accessrights = view.findViewById(R.id.accessrights);
        imagedescription = view.findViewById(R.id.imagedescription);
        txtterms = view.findViewById(R.id.txtterms);
        txtdescription = view.findViewById(R.id.txtdescription);
        chkinvoices = view.findViewById(R.id.chkinvoices);
        chkestimates = view.findViewById(R.id.chkestimates);
        chkreceipts = view.findViewById(R.id.chkreceipts);
        chkpurchase = view.findViewById(R.id.chkpurchase);
        chkpayment = view.findViewById(R.id.chkpayments);
        chkstock = view.findViewById(R.id.chkstock);
        chktax = view.findViewById(R.id.chktaxes);
        chksubadmin = view.findViewById(R.id.chksubadmin);
        adduser = view.findViewById(R.id.adduser);
        uploadimage = view.findViewById(R.id.uploadimage);
        setFonts();


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

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void setFonts() {
        imagedescription.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Light.ttf"));
        username.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        useremail.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkinvoices.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkestimates.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkreceipts.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkpurchase.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkpayment.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chkstock.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chktax.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        chksubadmin.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        adduser.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Ubuntu-Medium.ttf"));
        accessrights.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Fonts/AzoSans-Bold.otf"));
    }
}
