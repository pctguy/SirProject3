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

import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.Details.Customer_Detail_Activity;
import com.receipt.invoice.stock.sirproject.Details.User_Detail_Activity;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/15/2020.
 */



public class User_Listing_Adapter extends RecyclerView.Adapter<User_Listing_Adapter.ViewHolderForCat> {

    private Context mcontext ;
    List<String> musername=new ArrayList<>();
    List<String> muserrole=new ArrayList<>();
    List<Integer> mimages=new ArrayList<>();

    public User_Listing_Adapter(Context mcontext , ArrayList<String> username,ArrayList<String> userrole,ArrayList<Integer> images){
        this.mcontext = mcontext;
        musername=username;
        muserrole=userrole;
        mimages = images;
    }


    @NonNull
    @Override
    public User_Listing_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final User_Listing_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

        viewHolderForCat.username.setText(musername.get(i));
        viewHolderForCat.userrole.setText(muserrole.get(i));
        viewHolderForCat.image.setImageResource(mimages.get(i));

        viewHolderForCat.userdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext,User_Detail_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);            }
        });

    }
    @Override
    public int getItemCount() {
        return musername.size();
        //return 2;
    }


    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView username,userrole,userdetail;
        RoundedImageView image;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            userrole = itemView.findViewById(R.id.userrole);
            userdetail = itemView.findViewById(R.id.userdetail);
            image = itemView.findViewById(R.id.image);

            username.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));
            userrole.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Light.otf"));
            userdetail.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/Ubuntu-Light.ttf"));

        }

    }
    public void updateList(List<String> list){
        musername = list;
        notifyDataSetChanged();
    }
}