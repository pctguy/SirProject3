package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/1/2020.
 */


public class Products_Adapter extends RecyclerView.Adapter<Products_Adapter.ViewHolderForCat> {

    private Context mcontext ;
    List<String> mnames=new ArrayList<>();
    List<String> mquantity=new ArrayList<>();
    List<String> mprice=new ArrayList<>();

    public Products_Adapter(Context mcontext , ArrayList<String> names,ArrayList<String> quantity,ArrayList<String> price){
        this.mcontext = mcontext;
        mnames=names;
        mquantity=quantity;
        mprice=price;
    }


    @NonNull
    @Override
    public Products_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.products_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Products_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

        viewHolderForCat.name.setText(mnames.get(i));
        viewHolderForCat.price.setText(mprice.get(i));
        viewHolderForCat.quantity.setText("Qty: "+mquantity.get(i));

    }

    @Override
    public int getItemCount() {
        return mnames.size();
        //return 2;
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView name,price,quantity;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);

            name.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));
            price.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));
            quantity.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));

        }

    }

}
