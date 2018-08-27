package com.example.junhosung.robothunt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Junho Sung on 8/6/2018.
 */

public class MainMenuActivityFragment extends Fragment {

    Button btnPlay;
    Button btnOption;
    Button btnHelp;
    Button btnScoreboard;
    ImageView imgRobot;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu,container,false);



        btnPlay = (Button) view.findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),GameActivity.class);
                startActivity(intent);
            }
        });

        btnOption = (Button) view.findViewById(R.id.btn_option);
        btnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OptionActivity.class);
                startActivity(intent);
            }
        });


        btnHelp = (Button) view.findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        btnScoreboard = (Button) view.findViewById(R.id.btn_scoreboard);
        btnScoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ScoreboardActivity.class);
                startActivity(intent);

            }
        });

        imgRobot = (ImageView) view.findViewById(R.id.imgRobot);
        imgRobot.setImageResource(R.drawable.robotimage);

        return view;

    }
}
