package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.receipt.invoice.stock.sirproject.Details.Customer_Detail_Activity;
import com.receipt.invoice.stock.sirproject.Model.Product_list;
import com.receipt.invoice.stock.sirproject.Model.Tax_list;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.Tax.Add_Tax;
import com.receipt.invoice.stock.sirproject.Tax.Tax_Activity;
import com.receipt.invoice.stock.sirproject.Tax.Tax_Edit;
import com.receipt.invoice.stock.sirproject.Tax.Tax_Listing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/15/2020.
 */


public class Tax_Listing_Adapter extends RecyclerView.Adapter<Tax_Listing_Adapter.ViewHolderForCat> {

    private Context mcontext;
    ArrayList<Tax_list> mlist= new ArrayList<>();

    public Tax_Listing_Adapter(Context mcontext, ArrayList<Tax_list>list) {
        this.mcontext = mcontext;
        mlist = list;

    }


    @NonNull
    @Override
    public Tax_Listing_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tax_itemlayout, viewGroup, false);
        ViewHolderForCat viewHolderForCat = new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Tax_Listing_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

       /* viewHolderForCat.taxname.setText(mcnames.get(i));
        viewHolderForCat.taxpercent.setText(mnames.get(i));
        viewHolderForCat.taxtype.setText(maddresses.get(i));

        viewHolderForCat.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Tax_Activity.class);
                intent.putExtra("edit","edit");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);            }
        });*/


       final Tax_list tax_list = mlist.get(i);

       String tax_id = tax_list.getTax_id();
       String tax_name = tax_list.getTax_name();
       String company_name = tax_list.getCompany_name();
       if (tax_name.equals("") || tax_name.equals("null"))
       {
           viewHolderForCat.taxname.setText("N/A");
       }
       else
       {
           viewHolderForCat.taxname.setText(tax_name);
       }

       String tax_rate = tax_list.getTax_rate();
       if (tax_rate.equals("") || tax_rate.equals("null"))
       {
           viewHolderForCat.taxpercent.setText("N/A");
       }
       else
       {
           viewHolderForCat.taxpercent.setText(tax_rate+"%");
       }

        if (company_name.equals("") || company_name.equals("null"))
        {
            viewHolderForCat.companyname.setText("N/A");
        }
        else
        {
            viewHolderForCat.companyname.setText(company_name);
        }



        viewHolderForCat.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,Tax_Edit.class);
                intent.putExtra("edit","edit");
                intent.putExtra("companyid",tax_list.getCompanyid());
                intent.putExtra("taxid",tax_list.getTax_id());
                intent.putExtra("name",tax_list.getTax_name());
                intent.putExtra("rate",tax_list.getTax_rate());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
        //return 2;
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView taxname, taxpercent, companyname, edit;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            taxname = itemView.findViewById(R.id.taxname);
            taxpercent = itemView.findViewById(R.id.taxpercent);
            companyname = itemView.findViewById(R.id.companyname);
            edit = itemView.findViewById(R.id.edit);

            taxname.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/AzoSans-Medium.otf"));
            taxpercent.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/AzoSans-Light.otf"));
            companyname.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/AzoSans-Light.otf"));
            edit.setTypeface(Typeface.createFromAsset(mcontext.getAssets(), "Fonts/Ubuntu-Light.ttf"));

        }

    }

    public void updateList(ArrayList<Tax_list> list) {
        mlist = list;
        notifyDataSetChanged();
    }
}