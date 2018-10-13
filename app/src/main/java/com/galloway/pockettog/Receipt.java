package com.galloway.pockettog;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;

import java.io.InputStream;

import static android.provider.CalendarContract.CalendarCache.URI;


public class Receipt extends BaseActivity {

    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Button submitAnotherPhoto;
    Intent selectPhotoIntent;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.receipt);
        hamburgerMenu = findViewById(R.id.menu_icon);
        submitAnotherPhoto = findViewById(R.id.selectAgainButton);
        myIntent = getIntent();
        String email = myIntent.getStringExtra("emailAddress");
        String reference = myIntent.getStringExtra("confirmationCode");
        selectPhotoIntent = new Intent(this, SelectPhoto.class);

        TextView emailTv = findViewById(R.id.email);
        TextView referenceTv = findViewById(R.id.reference);
        String newEmail = emailTv.getText() + " " + email;
        String newReference = referenceTv.getText() + " " + reference;
        emailTv.setText(newEmail);
        referenceTv.setText(newReference);

        submitAnotherPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(selectPhotoIntent);
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
