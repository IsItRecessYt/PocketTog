package com.galloway.pockettog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import tools.ReceiptDialogFragment;


public class AddPhoto extends AppCompatActivity implements View.OnClickListener, BillingProcessor.IBillingHandler {
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab_exist, pay;
    private TextView memories_label;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    View cl;
    ImageView iv;;
    ImageView ivChildren;
    private final int SELECT_IMAGE_RESULT_REQUEST_CODE = 0;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        fab = findViewById(R.id.fab);
        fab_exist = findViewById(R.id.fab_exist);
        pay = findViewById(R.id.pay);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        memories_label = findViewById(R.id.memories_last);
        fab.setOnClickListener(this);
        fab_exist.setOnClickListener(this);
        pay.setOnClickListener(this);
        iv = findViewById(R.id.bg_image);
        ivChildren = findViewById(R.id.children_image);
        bp = new BillingProcessor(this, null, this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        pay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("Yoooooooooooooooooooo","FAB Clicked");
                bp.purchase(AddPhoto.this,"android.test.purchased");
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab_exist:
                openExistingPhotos();
                break;
        }
    }
    public void openCamera() {
        /*Intent takePhotoIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(takePhotoIntent,REQUEST_IMAGE_CAPTURE);*/
        Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
    }

    public void openExistingPhotos(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select A Picture to Beautify"),SELECT_IMAGE_RESULT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        switch(requestCode){
            case SELECT_IMAGE_RESULT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    InputStream inputStream;
                    Drawable bg;
                    ivChildren.setBackgroundDrawable(null);
                    try {
                        inputStream = getContentResolver().openInputStream(selectedImageUri);
                        bg = Drawable.createFromStream(inputStream, selectedImageUri.toString());
                        Toast.makeText(this, "Picture Selected", Toast.LENGTH_SHORT).show();
                        iv.setAlpha(1.0F);
                        iv.getLayoutParams().height = bg.getIntrinsicHeight();
                        iv.getLayoutParams().width = bg.getIntrinsicWidth();
                        iv.setBackgroundColor(Color.parseColor("#000000"));
                        iv.setBackgroundDrawable(bg);
                        iv.setVisibility(View.VISIBLE);
                        pay.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.INVISIBLE);
                        memories_label.setVisibility(View.INVISIBLE);
                        iv.requestLayout();
                        animateFAB();
                    } catch (FileNotFoundException e) {
                        bg = ContextCompat.getDrawable(this, R.drawable.weasenjace);
                        iv.setBackgroundDrawable(bg);
                        memories_label.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Picture Failed to Load", Toast.LENGTH_SHORT).show();
                    }

                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onDestroy(){
        if(bp != null){
            bp.release();
        }
        super.onDestroy();
    }


    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab_exist.startAnimation(fab_close);
            fab_exist.setClickable(false);
            fab_exist.setVisibility(View.INVISIBLE);
            isFabOpen = false;

        } else {

            fab.startAnimation(rotate_forward);
            fab_exist.startAnimation(fab_open);
            fab_exist.setClickable(true);
            fab_exist.setVisibility(View.VISIBLE);
            isFabOpen = true;

        }
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
        bp.consumePurchase("android.test.purchased");
        Toast.makeText(this, "Purchase Successful!", Toast.LENGTH_LONG).show();
        SimpleFTP ftp = new SimpleFTP();
        try
        {
            // Connect to an FTP server on port 21.

            ftp.connect("localhost", 14147);

            // Set binary mode.
            ftp.bin();

            // Change to a new working directory on the FTP server.
            ftp.cwd("C:/Users/mrfre/Desktop");

            // Upload some files.
            ftp.stor(new File("x.txt"));

            // Quit from the FTP server.
            ftp.disconnect();

        }
        catch (Exception e) {
            e.printStackTrace();
            pay.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);

        }
        memories_label.setVisibility(View.VISIBLE);
        Drawable bg;
        bg = ContextCompat.getDrawable(this, R.drawable.weasenjace);
        ivChildren.setBackgroundDrawable(bg);
        pay.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.INVISIBLE);
        ivChildren.setVisibility(View.VISIBLE);
        DialogFragment df = new ReceiptDialogFragment();
        df.show(getFragmentManager(),"receipt");
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
