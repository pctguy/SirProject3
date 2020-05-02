package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/1/2020.
 */

public class Business_Activities_Adapter extends RecyclerView.Adapter<Business_Activities_Adapter.ViewHolderForCat> {

    private Context mcontext ;
    List<String> mnames=new ArrayList<>();
    List<String> mquantity=new ArrayList<>();
    List<String> mprice=new ArrayList<>();

    public Business_Activities_Adapter(Context mcontext , ArrayList<String> names,ArrayList<String> quantity){
        this.mcontext = mcontext;
        mnames=names;
        mquantity=quantity;
    }


    @NonNull
    @Override
    public Business_Activities_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.businessactivities_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Business_Activities_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

        viewHolderForCat.invoicestxt.setText(mnames.get(i));
        viewHolderForCat.amount.setText(mquantity.get(i));

        viewHolderForCat.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"Coming Soon",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mnames.size();
        //return 2;
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView invoicestxt,amount;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            invoicestxt = itemView.findViewById(R.id.invoicestxt);
            amount = itemView.findViewById(R.id.amount);
            invoicestxt.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Regular.otf"));
            amount.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));

        }

    }

}