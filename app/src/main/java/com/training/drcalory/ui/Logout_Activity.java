package com.training.drcalory.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.training.drcalory.Login;
import com.training.drcalory.R;

public class Logout_Activity extends Fragment {

    Button logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_logout, container, false);

        logout = root.findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //On logout, we are removing the data from shared preference, and are directing user to
                //login page, so user has to login again
                SharedPreferences settings = getContext().getSharedPreferences("Pref",0 );
                settings.edit().clear().commit();

                SharedPreferences data = getContext().getSharedPreferences("ProfData",0 );
                data.edit().clear().commit();

                //intent is used to divert to a particular page
                Intent i = new Intent(getContext(), Login.class) ;
                startActivity(i);
            }
        });

        return root;
    }
}
