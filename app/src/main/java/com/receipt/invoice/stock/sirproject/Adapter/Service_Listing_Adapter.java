package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.receipt.invoice.stock.sirproject.Details.Customer_Detail_Activity;
import com.receipt.invoice.stock.sirproject.Details.Service_Detail_Activity;
import com.receipt.invoice.stock.sirproject.Model.Service_list;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/15/2020.
 */

public class Service_Listing_Adapter extends RecyclerView.Adapter<Service_Listing_Adapter.ViewHolderForCat> {

    private Context mcontext ;
    ArrayList<Service_list> mlist=new ArrayList<>();


    public Service_Listing_Adapter(Context mcontext , ArrayList<Service_list> list){
        this.mcontext = mcontext;
        mlist=list;
    }


    @NonNull
    @Override
    public Service_Listing_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Service_Listing_Adapter.ViewHolderForCat viewHolderForCat, final int i) {






        final Service_list service_list = mlist.get(i);


        String company_id = service_list.getCompany_id();
        final String service_id = service_list.getService_id();
        final String service_name = service_list.getService_name();
        final String service_price = service_list.getService_price();
        final String service_description = service_list.getService_description();
        final String service_taxable = service_list.getService_taxable();
        final String service_category = service_list.getService_category();
        String service_price_unit = service_list.getService_price_unit();
        final String measurement_unit = service_list.getMeasurement_unit();


        if (service_name.equals("") || service_name.equals("null"))
        {
            viewHolderForCat.servicename.setText("N/A");
        }
        else
        {
            viewHolderForCat.servicename.setText(service_name);
        }

        if (service_price.equals("") || service_price.equals("null"))
        {
            viewHolderForCat.servicecurrency.setText("N/A");
        }
        else
        {
            viewHolderForCat.servicecurrency.setText(service_price.replace(".00",""));

        }
        if (service_category.equals("") || service_category.equals("null"))
        {
            viewHolderForCat.servicecategory.setText("N/A");
        }
        else
        {
            viewHolderForCat.servicecategory.setText(service_category);

        }

        viewHolderForCat.servicecurrencyunit.setText(service_list.getCuurency_code());

        /*if (service_price_unit.equals("") || service_price_unit.equals("null"))
        {
            viewHolderForCat.productcategory.setText("Category:N/A");
        }
        else
        {
            viewHolderForCat.productcategory.setText("Category:"+service_price_unit);
        }*/


        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext,Service_Detail_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("service_id",service_id);
                intent.putExtra("service_name",service_name);
                intent.putExtra("service_price",service_price);
                intent.putExtra("service_description",service_description);
                intent.putExtra("service_taxable",service_taxable);
                intent.putExtra("service_category",service_category);
                intent.putExtra("currencycode",service_list.getCuurency_code());
                intent.putExtra("measurement_unit",measurement_unit);
                mcontext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mlist.size();
        //return 2;
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView servicename,servicecompany,servicecategory,servicecurrency,servicecurrencyunit;



        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            servicename = itemView.findViewById(R.id.servicename);
            servicecompany = itemView.findViewById(R.id.servicecompany);
            servicecategory = itemView.findViewById(R.id.servicecategory);
            servicecurrency = itemView.findViewById(R.id.servicecurrency);
            servicecurrencyunit = itemView.findViewById(R.id.servicecurrencyunit);

            servicename.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));
            servicecompany.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));
            servicecategory.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));
            servicecurrency.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Bold.ttf"));
            servicecurrencyunit.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Bold.ttf"));

        }

    }
    public void updateList(ArrayList<Service_list> list){
        mlist = list;
        notifyDataSetChanged();
    }
}
