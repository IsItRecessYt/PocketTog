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
import android.widget.ImageView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;



import java.io.File;
import java.io.InputStream;


import static android.provider.CalendarContract.CalendarCache.URI;


public class SubmitPhoto extends BaseActivity implements BillingProcessor.IBillingHandler  {

    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Button submitPhoto;
    Intent receiptIntent;
    BillingProcessor bp;
    Intent myIntent;
    Uri selectedImageUri;
    ImageView ivPicture;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.submit_image);

        myIntent = getIntent();
        hamburgerMenu = findViewById(R.id.menu_icon);
        submitPhoto = findViewById(R.id.submitButton);
        ivPicture = findViewById(R.id.ivPicture);
        bp = new BillingProcessor(this, null, this);
        String selectedImageUriString = myIntent.getStringExtra("selectedImage");
        InputStream inputStream;
        Drawable bg;
        receiptIntent = new Intent(this, Receipt.class);

        try {
            selectedImageUri = URI.parse(selectedImageUriString);
            inputStream = getContentResolver().openInputStream(selectedImageUri);
            bg = Drawable.createFromStream(inputStream, selectedImageUri.toString());
            System.out.println(selectedImageUri.toString());
            if(inputStream!=null) {
                inputStream.close();
            }
            ivPicture.setBackground(bg);
        }catch(Exception e){
            e.printStackTrace();
        }

        submitPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new AsyncBillingCall().execute("");
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

    private class AsyncBillingCall extends AsyncTask<String, String, String>{

        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Submitting...");

            try {

                bp.purchase(SubmitPhoto.this, "android.test.purchased");
                receiptIntent.putExtra("confirmationCode", "0000946259");
                receiptIntent.putExtra("emailAddress", "StevenPaulGalloway@gmail.com");
                return "";
            }catch(Exception e){
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(SubmitPhoto.this,
                    "Working..",
                    "We are communicating with servers...");
        }

        @Override
        protected void onProgressUpdate(String...text) {}

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if (!bp.handleActivityResult(requestCode, resultCode, data)){
        super.onActivityResult(requestCode, resultCode, data);
    }
        bp.consumePurchase("android.test.purchased");
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
        if(bp != null){
            bp.release();
        }
        super.onDestroy();
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        startActivity(receiptIntent);
}

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

}
