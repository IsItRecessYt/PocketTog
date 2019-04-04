package com.galloway.pockettog;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MenuFragment extends Fragment {

    GestureDetector gestureDetector;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hamburger_menu_content, container, false);


        Button homeButton = rootView.findViewById(R.id.homeButton);
        Button helpButton = rootView.findViewById(R.id.helpfuleHintsButton);
        Button examplesButton = rootView.findViewById(R.id.examplesButton);
        Button youtubeButton = rootView.findViewById(R.id.youtubePageButton);
        Button youtubeSocial = rootView.findViewById(R.id.youtubeButton);
        Button twitterSocial = rootView.findViewById(R.id.twitterButton);
        Button facebookSocial = rootView.findViewById(R.id.facebookButton);
        Button closeButton = rootView.findViewById(R.id.closeButton);
        closeButton.setSoundEffectsEnabled(false);
        final Intent homeIntent = new Intent(getActivity(), HomePage.class);
        final Intent helpIntent = new Intent(getActivity(), HelpfulHints.class);
        final Intent examplesIntent = new Intent(getActivity(), Examples.class);
        final Intent youtubeIntent = new Intent(getActivity(), Youtube.class);
        final Intent youtubeSocialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCd-RhS_o0jUqkuHj7qLRPbg"));
        final Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/TogPocket"));
        final Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/PocketTog"));

        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name = getActivity().getClass().getSimpleName();
                switch(name) {
                    case "HomePage":
                        ((HomePage) getActivity()).hideFragment();
                        break;
                    case "Examples":
                        ((Examples) getActivity()).hideFragment();
                        break;
                    case "HelpfulHints":
                        ((HelpfulHints) getActivity()).hideFragment();
                        break;
                    case "Receipt":
                        ((Receipt) getActivity()).hideFragment();
                        break;
                    case "SelectPhoto":
                        ((SelectPhoto) getActivity()).hideFragment();
                        break;
                    case "SubmitPhoto":
                        ((SubmitPhoto) getActivity()).hideFragment();
                        break;
                    case "Youtube":
                        ((Youtube) getActivity()).hideFragment();
                        break;
                }
            }
        });

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
                startActivity(youtubeSocialIntent);
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
        return rootView;

    }
}
