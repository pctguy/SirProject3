package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Company.Company_Listing;
import com.receipt.invoice.stock.sirproject.Constant.Constant;
import com.receipt.invoice.stock.sirproject.Details.Company_Details_Activity;
import com.receipt.invoice.stock.sirproject.Details.Company_Details_Fragment;
import com.receipt.invoice.stock.sirproject.Model.Company_list;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

public class Company_Listing_Adapter extends RecyclerView.Adapter<Company_Listing_Adapter.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<Company_list> mlist = new ArrayList<>();


    public Company_Listing_Adapter(Context mcontext, ArrayList<Company_list> list) {
        this.mcontext = mcontext;
        mlist = list;

    }


    @NonNull
    @Override
    public Company_Listing_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.company_itemlayout, viewGroup, false);
        ViewHolderForCat viewHolderForCat = new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Company_Listing_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

       /* viewHolderForCat.companyname.setText(mcnames.get(i));
        viewHolderForCat.address.setText(maddresses.get(i));
        viewHolderForCat.image.setImageResource(mimages.get(i));

        viewHolderForCat.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Company_Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });*/

        final SharedPreferences pref = mcontext.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
        Company_list company_list = mlist.get(i);

        final String company_id = company_list.getCompany_id();
        final String company_email = company_list.getCompany_email();
        final String company_phone = company_list.getCompany_phone();
        final String company_website = company_list.getCompany_website();
        final String currencyid = company_list.getCurrencyid();
        final String image_path = company_list.getCompany_image_path() + company_list.getCompany_logo();


        final String company_name = company_list.getCompany_name();
        if (company_name.equals("") && company_name.equals("null")) {
            viewHolderForCat.companyname.setText("N/A");
        } else {
            viewHolderForCat.companyname.setText(company_name);
        }


        final String company_address = company_list.getCompany_address();
        if (company_address.equals("") && company_address.equals("null")) {
            viewHolderForCat.address.setText("N/A");
        } else {

            if (company_address.length()>35){
                viewHolderForCat.address.setText(company_address.substring(0,33)+"...");
            }
            else {
                viewHolderForCat.address.setText(company_address);
            }
        }

        String logo = company_list.getCompany_logo();
       /* if (logo == null || logo.equals("") || logo.equals("null")) {
            viewHolderForCat.image.setImageResource(R.drawable.placeholder_big);
        } else {
            Glide.with(mcontext).load(image_path).into(viewHolderForCat.image);
        }*/
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(R.drawable.placeholder_big);
        Glide.with(mcontext)
                .load(image_path)
                .apply(options)
                .into(viewHolderForCat.image);

        /*viewHolderForCat.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Company_Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("company_id",company_id);
                intent.putExtra("company_name",company_name);
                intent.putExtra("company_image",image_path);
                intent.putExtra("company_email",company_email);
                intent.putExtra("company_phone",company_phone);
                intent.putExtra("company_website",company_website);
                intent.putExtra("company_address",company_address);
                mcontext.startActivity(intent);
            }
        });*/

        viewHolderForCat.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Company_Details_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                pref.edit().putString(Constant.COMPANY_NAME,company_name).commit();
                pref.edit().putString(Constant.COMPANY_LOGO,image_path).commit();
                pref.edit().putString(Constant.COMPANY_EMAIL,company_email).commit();
                pref.edit().putString(Constant.COMPANY_PHONE,company_phone).commit();
                pref.edit().putString(Constant.COMPANY_WEB,company_website).commit();
                pref.edit().putString(Constant.COMPANY_ID,company_id).commit();
                pref.edit().putString(Constant.COMPANY_ADDRESS,company_address).commit();
                pref.edit().putString(Constant.CURRENCY_ID,currencyid).commit();
                mcontext.startActivity(intent);
            }
        });






    }

    @Override
    public int getItemCount() {
        return mlist.size();
        //return 2;
    }
    //for removing redundancy
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    //end for removing redundancy

    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView companyname,name,address,details;
        RoundedImageView image;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            companyname = itemView.findViewById(R.id.companyname);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            details = itemView.findViewById(R.id.details);
            image = itemView.findViewById(R.id.image);

            companyname.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));
            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));
            address.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));
            details.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Light.ttf"));

        }

    }
    public void updateList(ArrayList<Company_list> list){
        mlist = list;
        notifyDataSetChanged();
    }
}