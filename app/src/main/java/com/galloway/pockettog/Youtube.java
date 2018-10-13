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
        video1Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Cb5bjlnDkCs"));
        video2Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=FBz81ssJV7g"));
        video3Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=qtxOvWndYfg"));
        video4Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=jAk_xhs0Rcw"));
        video5Intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=YtzK6zX7adU"));



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
