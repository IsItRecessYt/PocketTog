package com.galloway.pockettog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class NavigationBarContent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburger_menu_content);


        Button homeButton = findViewById(R.id.homeButton);
        Button helpButton = findViewById(R.id.helpfuleHintsButton);
        Button examplesButton = findViewById(R.id.examplesButton);
        Button youtubeButton = findViewById(R.id.youtubePageButton);
        Button youtubeSocial = findViewById(R.id.youtubeButton);
        Button twitterSocial = findViewById(R.id.twitterButton);
        Button facebookSocial = findViewById(R.id.facebookButton);
        final Intent homeIntent = new Intent(NavigationBarContent.this, HomePage.class);
        final Intent helpIntent = new Intent(NavigationBarContent.this, HelpfulHints.class);
        final Intent examplesIntent = new Intent(NavigationBarContent.this, HomePage.class);
        final Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
        final Intent youtubeSocialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
        final Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
        final Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));



        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(homeIntent);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(helpIntent);
            }
        });

        examplesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(examplesIntent);
            }
        });

        youtubeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(youtubeIntent);
            }
        });

        youtubeSocial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(youtubeIntent);
            }
        });

        twitterSocial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(twitterIntent);
            }
        });

        facebookSocial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               startActivity(facebookIntent);
            }
        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


}
