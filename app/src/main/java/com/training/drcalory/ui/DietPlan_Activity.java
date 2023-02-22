package com.training.drcalory.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.training.drcalory.ApiHandler.Apihandler;
import com.training.drcalory.Login;
import com.training.drcalory.MainActivity;
import com.training.drcalory.Model.LoginModel;
import com.training.drcalory.Model.getDailyModel;
import com.training.drcalory.R;

import org.apache.commons.lang3.StringUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class DietPlan_Activity extends Fragment {

    EditText etBreakfast,etLunch, etSnack,etDinner;
    Button refresh;
    String breakfast,lunch,snack,dinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_diet_plan, container, false);

        etBreakfast = root.findViewById(R.id.etBreakfast);
        etLunch = root.findViewById(R.id.etLunch);
        etSnack = root.findViewById(R.id.etSnack);
        etDinner = root.findViewById(R.id.etDinner);

        refresh = root.findViewById(R.id.btnRefresh);


        SharedPreferences pref = getActivity().getSharedPreferences("Pref", 0);
        final String mail = pref.getString("sharedEmail", "null");

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Apihandler.getApiService().getDaily(mail, new Callback<LoginModel>() {
                    @Override
                    public void success(LoginModel loginmodel, Response response) {
                        String value = new String(((TypedByteArray) response.getBody()).getBytes());

                        //from the response, we get an array,
                        //so from an array we will divide into parts to get the individual element of breakfast, lunch, dinner
                        breakfast = StringUtils.substringBetween(value, "breakfast\":\"", "\",");
                        etBreakfast.setText(breakfast);

                        lunch = StringUtils.substringBetween(value, "lunch\":\"", "\",");
                        etLunch.setText(lunch);

                        snack = StringUtils.substringBetween(value, "snack\":\"", "\",");
                        etSnack.setText(snack);

                        dinner = StringUtils.substringBetween(value, "dinner\":\"", "\"");
                        etDinner.setText(dinner);

                        Toast.makeText(getContext(), "Successfully Updated: ", Toast.LENGTH_LONG).show();
                    }
                    //if it fails, then failure method will be called
                    @Override
                    public void failure(RetrofitError error) {
                        String er = error.toString();
                        Toast.makeText(getContext(), "Failed: " + er, Toast.LENGTH_LONG).show();
                        Log.d("<>","in fail");
                    }
                });

            }
        });

        return root;
    }
}
