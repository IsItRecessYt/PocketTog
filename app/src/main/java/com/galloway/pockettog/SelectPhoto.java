package com.galloway.pockettog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SelectPhoto extends BaseActivity {

    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Button selectPhoto;
    TextView rememberTitle;
    TextView rememberSubmission;
    Animation animation;;
    private float x1,x2;
    private final int SELECT_IMAGE_RESULT_REQUEST_CODE = 0;
    static final int MIN_DISTANCE = 150;
    Intent selectPhotoIntent;
    Intent homeIntent;
    Intent submitPhotoIntent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.select_photo);

        hamburgerMenu = findViewById(R.id.menu_icon);

        rememberTitle = findViewById(R.id.rememberTitle);
        homeIntent = new Intent(this,HomePage.class);
        rememberSubmission = findViewById(R.id.rememberSubmission);
        selectPhoto = findViewById(R.id.selectPhoto);
        animation = AnimationUtils.loadAnimation(this, R.anim.text_fade);
        rememberSubmission.startAnimation(animation);
        selectPhotoIntent = new Intent();
        submitPhotoIntent = new Intent(this, SubmitPhoto.class);
        selectPhotoIntent.setType("image/*");
        selectPhotoIntent.setAction(Intent.ACTION_GET_CONTENT);


        selectPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityForResult(Intent.createChooser(selectPhotoIntent, "Select A Picture To Send For Edits"),SELECT_IMAGE_RESULT_REQUEST_CODE);
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
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE)
                {
                    // Left to Right swipe action
                    if (x2 > x1)
                    {
                        swipeRight();
                    }

                    // Right to left swipe action
                    else
                    {
                        swipeLeft();
                    }

                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    public void swipeLeft(){
        startActivityForResult(Intent.createChooser(selectPhotoIntent, "Select A Picture To Send For Edits"),SELECT_IMAGE_RESULT_REQUEST_CODE);
    }

    public void swipeRight(){
        startActivity(homeIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    submitPhotoIntent.putExtra("selectedImage", selectedImageUri.toString());
                    startActivity(submitPhotoIntent);

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "User cancelled selection", Toast.LENGTH_SHORT).show();
                }
    }

}
