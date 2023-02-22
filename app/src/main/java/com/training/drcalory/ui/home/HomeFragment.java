package com.training.drcalory.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.training.drcalory.About_Mithar;
import com.training.drcalory.About_Program;
import com.training.drcalory.About_Stage;
import com.training.drcalory.R;

import pl.droidsonroids.gif.GifImageView;

public class HomeFragment extends Fragment{

    private ViewFlipper v_flipper;
    private ImageView mithar;
    private GifImageView slideGif;
    private TextView stage_process;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mithar = (ImageView) root.findViewById(R.id.mitharLogo);
        slideGif = root.findViewById(R.id.slideGif);
        stage_process = root.findViewById(R.id.stage_process);

        mithar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), About_Mithar.class);
                startActivity(i);
            }
        });

        slideGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(getActivity(), About_Program.class);
                startActivity(i);
            }
        });

        stage_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), About_Stage.class);
                startActivity(i);
            }
        });

        int images[] = {R.drawable.balanced_diet, R.drawable.consult2, R.drawable.eat_healthy, R.drawable.image3, R.drawable.image2};

        v_flipper = root.findViewById(R.id.v_flipper);

        for(int image:images){
            flipperImages(image);
        }

        return root;
    }

    //image carousal
    public void flipperImages(int image){

        ImageView imageView = new ImageView(this.getContext());
        imageView.setBackgroundResource(image);

        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);
        v_flipper.setAutoStart(true);

        v_flipper.setInAnimation(this.getContext(),android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(this.getContext(),android.R.anim.slide_out_right);
    }
}