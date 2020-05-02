package com.receipt.invoice.stock.sirproject.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.receipt.invoice.stock.sirproject.Company.Companies_Activity;
import com.receipt.invoice.stock.sirproject.Customer.Customer_Activity;
import com.receipt.invoice.stock.sirproject.Home.Home_Activity;
import com.receipt.invoice.stock.sirproject.Home.Settings_Activity;
import com.receipt.invoice.stock.sirproject.Invoice.Create_Invoice_Activity;
import com.receipt.invoice.stock.sirproject.Product.Product_Activity;
import com.receipt.invoice.stock.sirproject.R;
import com.receipt.invoice.stock.sirproject.Service.Service_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signin_Activity;
import com.receipt.invoice.stock.sirproject.SignupSignin.Signup_Activity;
import com.receipt.invoice.stock.sirproject.Stock.Stock_Activity;
import com.receipt.invoice.stock.sirproject.Tax.Tax_Activity;
import com.receipt.invoice.stock.sirproject.User.User_Activity;
import com.receipt.invoice.stock.sirproject.Vendor.Vendor_Activity;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Fawad on 3/31/2020.
 */

public class Constant {
    public static String BASE_URL = "https://infinitmobility.com/sir/api/";
   // public static String BASE_URL = "https://infinitmobility.com/sir/index.php/api/";
    public static String ACCESS_TOKEN = "mndnkdcnn";
    public static String FULLNAME = "fn";
    public static String EMAIL = "el";
    public static String PREF_BASE = "hdkjhkad";
    public static final String FIRST_TIME="ft";
    public static final String LOGGED_IN = "lin";
    public static final String COMPANY_NAME = "jdch";
    public static final String COMPANY_LOGO = "kdckjhdckhdh";
    public static final String COMPANY_EMAIL = "sdhjhds";
    public static final String COMPANY_PHONE = "jhdxbj";
    public static final String COMPANY_WEB = "ncxbzhjbkjchxkk";
    public static final String COMPANY_ID = "vhgzxjhghjxgzjhgxg";
    public static final String COMPANY_ADDRESS = "dcjkgkdhghjgdhcghdcjh";
    public static final String CURRENCY_ID = "ci";




    public static void toolbar(final Activity activity, String title){

        String username = Constant.GetSharedPreferences(activity, Constant.FULLNAME);
        String email = Constant.GetSharedPreferences(activity, Constant.EMAIL);
        AccountHeader header = null;
        Typeface myfont=Typeface.createFromAsset(activity.getAssets(),"Fonts/Ubuntu-Regular.ttf");

                header = new AccountHeaderBuilder()
                        .withActivity(activity)
                        .withAlternativeProfileHeaderSwitching(true)
                        .withCompactStyle(true)
                        .withPaddingBelowHeader(true)
                        .withHeightDp(115)
                        .withDividerBelowHeader(false)
                        //.withPaddingBelowHeader(true)

                        .withSelectionListEnabledForSingleProfile(false)
                        .addProfiles(new ProfileDrawerItem().withName(username).withEmail(email).withTextColor(activity.getResources().getColor(R.color.white))
                                .withTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/AzoSans-Regular.otf"))
                                .withIcon(R.drawable.sir_app_logo))

                        .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                            @Override
                            public boolean onClick(View view, IProfile profile) {

                                /*Intent intent = new Intent(activity, AgentProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);*/

                                return true;

                            }
                        }).build();




        //Drawer Items
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(1).withIcon(R.drawable.add_user_icon).withName("Add New User").withTextColor(Color.WHITE);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withIcon(R.drawable.add_company_icon).withName("My Companies").withTextColor(Color.WHITE);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withIcon(R.drawable.my_products_icon).withName("My Products").withTextColor(Color.WHITE);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withIcon(R.drawable.my_services_icon).withName("My Services").withTextColor(Color.WHITE);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withIcon(R.drawable.add_taxes_icon).withName("Add Taxes").withTextColor(Color.WHITE);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withIcon(R.drawable.customers_icon).withName("Customers").withTextColor(Color.WHITE);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(5).withIcon(R.drawable.vendors_icon).withName("Suppliers").withTextColor(Color.WHITE);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(5).withIcon(R.drawable.stock_icon).withName("Stocks").withTextColor(Color.WHITE);
        //2 space
        PrimaryDrawerItem item9 = new PrimaryDrawerItem().withIdentifier(5).withIcon(R.drawable.settings_icon).withName("Settings").withTextColor(Color.WHITE);
        PrimaryDrawerItem item10 = new PrimaryDrawerItem().withIdentifier(5).withIcon(R.drawable.logout_icon).withName("Logout").withTextColor(Color.WHITE);

        PrimaryDrawerItem item11 = new PrimaryDrawerItem().withIdentifier(5).withName("").withTextColor(Color.WHITE);
        PrimaryDrawerItem item12 = new PrimaryDrawerItem().withIdentifier(5).withName("").withTextColor(Color.WHITE);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        final Drawer result = new DrawerBuilder()
                .withAccountHeader(header)
                .withActivity(activity)
                .withHeaderPadding(true)
                .withDrawerWidthPx((width/2)+((width/2)/2))

                //.withDrawerGravity(Gravity.RIGHT)
                //.withSliderBackgroundColorRes(R.color.sidemenublue)
                .withSliderBackgroundDrawable(activity.getResources().getDrawable(R.drawable.side_menu_bg))
                .withSelectedItem(-1)
                .addDrawerItems(
                        item11.withSelectable(false).withTypeface(myfont),
                        item.withSelectable(false).withTypeface(myfont),
                        item2.withSelectable(false).withTypeface(myfont),
                        item3.withSelectable(false).withTypeface(myfont),
                        item4.withSelectable(false).withTypeface(myfont),
                        item5.withSelectable(false).withTypeface(myfont),
                        item6.withSelectable(false).withTypeface(myfont),
                        item7.withSelectable(false).withTypeface(myfont),
                        item8.withSelectable(false).withTypeface(myfont),

                        item12.withSelectable(false).withTypeface(myfont),

                        item9.withSelectable(false).withTypeface(myfont),
                        item10.withSelectable(false).withTypeface(myfont)
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    //Actions to be performed after clicking drawer items
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if(position==2)
                        {

                            Intent intent = new Intent(activity,User_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);

                        }

                        if(position==3)
                        {

                                Intent intent = new Intent(activity,Companies_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                activity.startActivity(intent);


                        }

                        if(position==4)
                        {

                            Intent intent = new Intent(activity, Product_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }

                        if(position==5) {

                            Intent intent = new Intent(activity, Service_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }

                        if(position==6){

                            Intent intent = new Intent(activity, Tax_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                        }
                        if(position==7)
                        {

                            Intent intent = new Intent(activity, Customer_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);

                        }
                        if(position==8)
                        {
                            Intent intent = new Intent(activity, Vendor_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);


                        }
                        if(position==9)
                        {
                            Intent intent = new Intent(activity, Stock_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);



                        }
                        if(position==11)
                        {
                            Intent intent = new Intent(activity,Settings_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);

                        }

                        if(position==12)
                        {
                           /* SharedPreferences preferences = activity.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                            preferences.edit().remove(Constant.LOGGED_IN).commit();
                            preferences.edit().remove(Constant.ROLE).commit();
                            preferences.edit().remove(Constant.USER_ID).commit();
                            preferences.edit().remove(Constant.PROFILE_IMAGE).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_ID).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_NAME).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_NUMBER).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_ADDRESS).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_COUNTRY_NAME).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_COUNTRY_ID).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_CITY_NAME).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_CITY_ID).commit();
                            preferences.edit().remove(Constant.LOGIN_AGENCY_EMAIL).commit();
                            preferences.edit().remove(Constant.EMAIL).commit();
                            preferences.edit().remove(Constant.FULLNAME).commit();
                            preferences.edit().remove(Constant.PHONE_NUMBER).commit();
                            preferences.edit().remove(Constant.ACCESS_TOKEN).commit();
                            preferences.edit().remove(Constant.IMAGE_PATH).commit();
                            preferences.edit().remove(Constant.LOGIN_user_ID).commit();
                            preferences.edit().remove(Constant.IMAGE_PATH).commit();

                            LoginManager.getInstance().logOut();*/
                            SharedPreferences preferences = activity.getSharedPreferences(Constant.PREF_BASE,MODE_PRIVATE);
                            preferences.edit().remove(Constant.LOGGED_IN).commit();

                            Intent intent = new Intent(activity, Signin_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);

                        }


                        return false;
                    }
                })
                .build();

        //tool bar
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        TextView titleView = toolbar.findViewById(R.id.title);
        ImageView backbtn = toolbar.findViewById(R.id.backbtn);
        ImageView ham = toolbar.findViewById(R.id.ham);
        titleView.setText(title);
        titleView.setTypeface(Typeface.createFromAsset(activity.getAssets(),"Fonts/AzoSans-Bold.otf"));

            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    activity.onBackPressed();
                }
            });

            ham.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (result.isDrawerOpen()) {

                        result.closeDrawer();
                    } else {
                        result.openDrawer();
                    }                }
            });


        if (title.equals("Customers")){
                ham.setVisibility(View.VISIBLE);
                backbtn.setVisibility(View.GONE);

            }
            else if (title.equals("Vendors")){
                ham.setVisibility(View.VISIBLE);
                backbtn.setVisibility(View.GONE);

            }
        else if (title.equals("")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);

        }
/*
        else if (title.equals("Companies")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Details")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Taxes")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Services")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Products")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Users")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
        else if (title.equals("Stock")){
            ham.setVisibility(View.VISIBLE);
            backbtn.setVisibility(View.GONE);
        }
*/

    }

    public static void bottomNav(final Activity activity, int position){



        ImageView addproperty = activity.findViewById(R.id.addprop);

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) activity.findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.nav_home_icon, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.nav_receipts_icon, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.nav_reports_icon, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("", R.drawable.nav_profile_icon, R.color.white);
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        addproperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, Create_Invoice_Activity.class);
                activity.startActivity(intent);

            }
        });

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                if(position==0){
                    Intent intent = new Intent(activity, Home_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
                else if(position==1){


                    Toast.makeText(activity,"Coming soon",Toast.LENGTH_SHORT).show();
                       /* Intent intent = new Intent(activity, Create_Invoice_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);
*/

                }
                else if(position==2){

                    Toast.makeText(activity,"Coming soon",Toast.LENGTH_SHORT).show();

                        /*Intent intent = new Intent(activity, MyFavoritesActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);*/


                }

                else if(position==3){

                    Toast.makeText(activity,"Coming soon",Toast.LENGTH_SHORT).show();

                       /* Intent intent = new Intent(activity, MyProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(intent);*/




                }

                return false;
            }
        });

        //bottomNavigation.setDefaultBackgroundResource(R.drawable.bottomnavlayout);
    /*   bottomNavigation.setBehaviorTranslationEnabled(false);
       bottomNavigation.setAccentColor(R.color.red);
       bottomNavigation.setInactiveColor(Color.parseColor("#c5c5c5"));*/
        //bottomNavigation.setInactiveColor(Color.parseColor("#ffffff"));
        bottomNavigation.setCurrentItem(position, false);
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(Color.parseColor("#1B60FB"));
        bottomNavigation.setInactiveColor(Color.parseColor("#6695FF"));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

    }

    public static void SuccessToast(Activity context,String message){
        new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("\nSuccess")
                .titleTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/AzoSans-Bold.otf"))
                .titleSizeInSp(15)
                .message(message)
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(context.getResources().getDrawable(R.drawable.success_toast_background))
                .duration(2000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }

    public static void ErrorToast(Activity context,String message){
        new Flashbar.Builder(context)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("\nError")
                .titleTypeface(Typeface.createFromAsset(context.getAssets(),"Fonts/AzoSans-Bold.otf"))
                .titleSizeInSp(15)
                .message(message)
                //.messageTypeface(Typeface.createFromAsset(getAssets(),"fonts/CatamaraBold.ttf"))
                .backgroundDrawable(context.getResources().getDrawable(R.drawable.error_toast_background))
                .duration(3000)
                .enableSwipeToDismiss()
                .castShadow(false)
                .enterAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(context)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build().show();
    }

    public static String GetSharedPreferences(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(Constant.PREF_BASE,Context.MODE_PRIVATE);
        String val="";
        if (preferences.contains(key)){
            val = preferences.getString(key,"");
        }
        Log.e("valuFromPrefs",val);
        return val;
    }
}
