package com.galloway.pockettog;

import android.content.Intent;
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


public class HelpfulHints extends BaseActivity {

    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Integer helpNumber = 0;
    Button letsGo;
    Button backButton;
    TextView hintText;
    TextView hintNumber;
    Animation animation;;
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.helpful_hints);

        hamburgerMenu = findViewById(R.id.menu_icon);

        hintText = findViewById(R.id.helpfulHintsText);
        hintNumber = findViewById(R.id.helpfulHintsCount);
        letsGo = findViewById(R.id.letsGo);
        backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.GONE);
        animation = AnimationUtils.loadAnimation(this, R.anim.text_fade);
        hintText.startAnimation(animation);
        hintNumber.startAnimation(animation);

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

        letsGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateHint(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateHint(false);
            }
        });



    }

    public void updateHint(Boolean moveForward){
        if(moveForward) {
            switch(helpNumber) {
                case 0:
                    hintNumber.setText("1/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText1));
                    backButton.setVisibility(View.VISIBLE);
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint2));
                    break;
                case 1:
                    hintNumber.setText("2/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText2));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint3));
                    break;
                case 2:
                    hintNumber.setText("3/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText3));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint4));
                    break;
                case 3:
                    hintNumber.setText("4/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText4));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint0));
                    break;
                case 4:
                    hintNumber.setText("0/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText0));
                    backButton.setVisibility(View.GONE);
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.letsGo));
                    helpNumber=-1;
                    break;
            }
            helpNumber++;
        }else{
            switch(helpNumber) {
                case 0:
                    Intent homeIntent = new Intent(this, HomePage.class);
                    startActivity(homeIntent);
                    break;
                case 1:
                    hintNumber.setText("0/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText0));
                    backButton.setVisibility(View.GONE);
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.letsGo));
                    break;
                case 2:
                    hintNumber.setText("1/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText1));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint2));
                    break;
                case 3:
                    hintNumber.setText("2/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText2));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint3));
                    break;
                case 4:
                    hintNumber.setText("3/4");
                    hintText.setText(getResources().getString(R.string.helpfulHintsText3));
                    hintText.startAnimation(animation);
                    hintNumber.startAnimation(animation);
                    letsGo.setText(getResources().getString(R.string.hint4));
                    break;
            }
            helpNumber--;

        }
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
                        updateHint(false);
                    }

                    // Right to left swipe action
                    else
                    {
                        updateHint(true);
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


}
