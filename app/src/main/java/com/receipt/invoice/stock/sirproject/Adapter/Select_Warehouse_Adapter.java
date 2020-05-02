package com.receipt.invoice.stock.sirproject.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fawad on 4/15/2020.
 */
//Select_Warehouse_Adapter

public class Select_Warehouse_Adapter extends RecyclerView.Adapter<Select_Warehouse_Adapter.ViewHolderForCat> {

    private Context mcontext ;
    List<String> mtxtwarehouse=new ArrayList<>();
    List<String> mwarehouseid=new ArrayList<>();
    ArrayList<String> selectedlist = new ArrayList<>();
    Callback callback;
    TextView mdone;

    public Select_Warehouse_Adapter(Context mcontext , ArrayList<String>txtwarehouse,ArrayList<String>warehouseid,TextView done,Callback callback){
        this.mcontext = mcontext;
        mtxtwarehouse=txtwarehouse;
        this.callback=callback;
        mdone=done;
        mwarehouseid = warehouseid;

    }


    @NonNull
    @Override
    public Select_Warehouse_Adapter.ViewHolderForCat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View myitem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.selectwarehouse_itemlayout , viewGroup , false);
        ViewHolderForCat viewHolderForCat =new ViewHolderForCat(myitem);
        return viewHolderForCat;
    }

    @Override
    public void onBindViewHolder(@NonNull final Select_Warehouse_Adapter.ViewHolderForCat viewHolderForCat, final int i) {

        viewHolderForCat.txtwarehouse.setText(mtxtwarehouse.get(i));

        viewHolderForCat.txtwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolderForCat.imgchk.getVisibility()==View.GONE)
                {
                    viewHolderForCat.imgchk.setVisibility(View.VISIBLE);
                    selectedlist.add(mwarehouseid.get(i));
                }
                else if (viewHolderForCat.imgchk.getVisibility()==View.VISIBLE)
                {
                    viewHolderForCat.imgchk.setVisibility(View.GONE);
                    selectedlist.remove(mwarehouseid.get(i));
                }
            }
        });


        mdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onWarehouseSelected(selectedlist.size(),selectedlist);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mtxtwarehouse.size();
        //return 2;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolderForCat extends RecyclerView.ViewHolder {


        TextView txtwarehouse;
        ImageView imgchk;


        public ViewHolderForCat(@NonNull View itemView) {
            super(itemView);

            txtwarehouse = itemView.findViewById(R.id.txtwarehouse);
            imgchk = itemView.findViewById(R.id.imgchk);

            txtwarehouse.setTypeface(Typeface.createFromAsset(mcontext.getAssets(),"Fonts/AzoSans-Medium.otf"));


        }

    }

    public interface Callback{
        void onWarehouseSelected(int count,ArrayList<String> warehouseList);
    }
}
