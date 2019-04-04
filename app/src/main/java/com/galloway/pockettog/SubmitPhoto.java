package com.galloway.pockettog;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


import static android.provider.CalendarContract.CalendarCache.URI;


public class SubmitPhoto extends BaseActivity implements BillingProcessor.IBillingHandler  {


    /*
    Encrypt tokens
    Make submit happen after purchase
    Build store for purchase
    Clean up all code bases

     */
    boolean isFragmentLoaded;
    Fragment menuFragment;
    ImageView hamburgerMenu;
    Button submitPhoto;
    Intent receiptIntent;
    BillingProcessor bp;
    Intent myIntent;
    Uri selectedImageUri;
    ImageView ivPicture;
    String email;
    InputStream inputStream;
    Bitmap bitmap2;

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
        Drawable bg;
        receiptIntent = new Intent(this, Receipt.class);


        try {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;
            selectedImageUri = URI.parse(selectedImageUriString);
            inputStream = getContentResolver().openInputStream(selectedImageUri);
            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
            bitmap2 = bitmap;
            if(bitmap.getHeight()>bitmap.getWidth()){//is portrait
                float ratio = ((float) bitmap.getWidth() / (float) bitmap.getHeight());
                float newWidthFloat = (screenHeight-120)*ratio;
                int newWidth = Math.round(newWidthFloat);
                System.out.println(ratio);
                System.out.println(bitmap.getWidth());
                System.out.println(bitmap.getHeight());
                System.out.println(newWidth);
                System.out.println(screenHeight-120);
                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth-120, screenHeight-120, true);
                ivPicture.getLayoutParams().height = bitmap.getHeight();
                ivPicture.getLayoutParams().width = bitmap.getWidth();

            }else {//is landscape
                float ratio = ((float) bitmap.getHeight() / (float) bitmap.getWidth());
                float newHeightFloat = screenWidth*ratio;
                int newHeight = Math.round(newHeightFloat);
                bitmap = Bitmap.createScaledBitmap(bitmap, screenWidth, newHeight, true);
                ivPicture.getLayoutParams().height = bitmap.getHeight();
                ivPicture.getLayoutParams().width = bitmap.getWidth();
            }
            ivPicture.setImageBitmap(bitmap);
        }catch(Exception e){
            e.printStackTrace();
        }

        submitPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                callGoogleEmailIntent();
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
                String blobName = System.currentTimeMillis() + email + ".jpg";
                bp.purchase(SubmitPhoto.this, "android.test.purchased");

                String connectionString = "DefaultEndpointsProtocol=https;AccountName=pockettogphotos;AccountKey=oIvqB8oLy2CDTxMC4D29totsygRY/QZDmp7qx9MVixWDyMlae9GAnymq2wuJvr3fdLJZD8nU0Z6Y7FsCTXNtUg==;EndpointSuffix=core.windows.net";

                CloudStorageAccount storageAccount = CloudStorageAccount.parse(connectionString);

                // Create the blob client
                CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                // Get a reference to a container
                // The container name must be lower case
                CloudBlobContainer container = blobClient.getContainerReference("uneditedphotos");

                /*final String filePath = "storage/emulated/0/DCIM/Camera/IMG_20190328_141613.jpg";
                CloudBlockBlob blob = container.getBlockBlobReference(blobName);
                File source = new File(filePath);
                blob.upload(new FileInputStream(source), source.length());*/


                File f = new File(getApplicationContext().getCacheDir(), blobName);
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG,100, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
                CloudBlockBlob blob = container.getBlockBlobReference(blobName);
                File source = new File(f.getAbsolutePath());
                System.out.println(source);
                blob.upload(new FileInputStream(source), source.length());

                receiptIntent.putExtra("emailAddress", email);
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

        if(requestCode!=100) {
            if (!bp.handleActivityResult(requestCode, resultCode, data)) {
                super.onActivityResult(requestCode, resultCode, data);
            }

            bp.consumePurchase("android.test.purchased");
        }else {
            if(resultCode == RESULT_OK) {
                email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                if(email.length()<101) {
                    new AsyncBillingCall().execute("");
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Email must be less than 100 characters", Toast.LENGTH_LONG);
                    toast.show();
                    callGoogleEmailIntent();
                }
            }
        }
    }

    public void callGoogleEmailIntent(){
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                true, null, null, null, null);
        startActivityForResult(googlePicker, 100);
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
