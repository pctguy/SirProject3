package com.receipt.invoice.stock.sirproject.User;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.receipt.invoice.stock.sirproject.Adapter.User_Listing_Adapter;
import com.receipt.invoice.stock.sirproject.R;

import java.util.ArrayList;
import java.util.List;

public class User_Listing extends android.support.v4.app.Fragment {



    public User_Listing() {
        // Required empty public constructor
    }

    RecyclerView recycleruser;
    ArrayList<String> username=new ArrayList<>();
    ArrayList<String> userrole=new ArrayList<>();
    ArrayList<Integer> images=new ArrayList<>();
    User_Listing_Adapter user_Listing_Adapter;
    EditText search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user__listing, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        recycleruser = view.findViewById(R.id.recycleruser);
        search = view.findViewById(R.id.search);
        search.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Fonts/AzoSans-Light.otf"));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (username.size()>0){
                    filter(s.toString());
                }
            }
        });


        username.add("John smith");
        username.add("Mark Twain");
        username.add("Judy Lawless");
        username.add("Faris Hamed");

        userrole.add("Super Admin");
        userrole.add("Super Admin");
        userrole.add("Super Admin");
        userrole.add("Super Admin");




        images.add(R.drawable.user_01);
        images.add(R.drawable.user_02);
        images.add(R.drawable.user_03);
        images.add(R.drawable.user_04);


        user_Listing_Adapter = new User_Listing_Adapter(getContext(),username,userrole,images);
        recycleruser.setAdapter(user_Listing_Adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycleruser.setLayoutManager(layoutManager);
        recycleruser.setHasFixedSize(true);
        user_Listing_Adapter.notifyDataSetChanged();


        return view;
    }
    void filter(String text){
        List<String> temp = new ArrayList();
        for(String  d: username){
            if(d.toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        user_Listing_Adapter.updateList(temp);
    }

}
