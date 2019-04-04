package com.galloway.pockettog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import java.io.InputStream;

import static android.provider.CalendarContract.CalendarCache.URI;


public class Youtube extends BaseActivity {

    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Intent video1Intent;
    Intent video2Intent;
    Intent video3Intent;
    Intent video4Intent;
    Intent video5Intent;
    ImageButton video1;
    ImageButton video2;
    ImageButton video3;
    ImageButton video4;
    ImageButton video5;
    TextView videoDesc1;
    TextView videoDesc2;
    TextView videoDesc3;
    TextView videoDesc4;
    TextView videoDesc5;
    TextView videoTitle1;
    TextView videoTitle2;
    TextView videoTitle3;
    TextView videoTitle4;
    TextView videoTitle5;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.youtube);
        hamburgerMenu = findViewById(R.id.menu_icon);
        video1 = findViewById(R.id.imageView1);
        video2 = findViewById(R.id.imageView2);
        video3 = findViewById(R.id.imageView3);
        video4 = findViewById(R.id.imageView4);
        video5 = findViewById(R.id.imageView5);
        videoDesc1 = findViewById(R.id.description1);
        videoDesc2 = findViewById(R.id.description2);
        videoDesc3 = findViewById(R.id.description3);
        videoDesc4 = findViewById(R.id.description4);
        videoDesc5 = findViewById(R.id.description5);
        videoTitle1 = findViewById(R.id.title1);
        videoTitle2 = findViewById(R.id.title2);
        videoTitle3 = findViewById(R.id.title3);
        videoTitle4 = findViewById(R.id.title4);
        videoTitle5 = findViewById(R.id.title5);
        video1Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        video2Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        video3Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        video4Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));
        video5Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/"));

        video2.setVisibility(View.GONE);
        videoTitle2.setVisibility(View.GONE);
        videoDesc2.setVisibility(View.GONE);


        video3.setVisibility(View.GONE);
        videoTitle3.setVisibility(View.GONE);
        videoDesc3.setVisibility(View.GONE);


        video4.setVisibility(View.GONE);
        videoTitle4.setVisibility(View.GONE);
        videoDesc4.setVisibility(View.GONE);


        video5.setVisibility(View.GONE);
        videoTitle5.setVisibility(View.GONE);
        videoDesc5.setVisibility(View.GONE);

        video1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(video1Intent);
            }
        });

        video2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(video2Intent);
            }
        });

        video3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(video3Intent);
            }
        });

        video4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(video4Intent);
            }
        });

        video5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(video5Intent);
            }
        });

        hamburgerMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isFragmentLoaded){
                    loadFragment();
                }
                else{
                    if(menuFragment !=null){
                        if(menuFragment.isAdded()){
                            hideFragment();
                        }
                    }
                }

            }
        });


    }

    public void hideFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        fragmentTransaction.remove(menuFragment);
        fragmentTransaction.commit();
        hamburgerMenu.setImageResource(R.drawable.hamburger_menu);
        isFragmentLoaded = false;
    }
    public void loadFragment(){
        FragmentManager fm = getSupportFragmentManager();
        menuFragment = fm.findFragmentById(R.id.container);
        hamburgerMenu.setImageResource(R.drawable.back_arrow_blue);
        if(menuFragment == null){
            menuFragment = new MenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
            fragmentTransaction.add(R.id.container, menuFragment);
            fragmentTransaction.commit();
        }

        isFragmentLoaded = true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
