package com.training.drcalory.ui;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.training.drcalory.ApiHandler.Apihandler;
import com.training.drcalory.Model.ProfileModel;
import com.training.drcalory.Model.SignupModel;
import com.training.drcalory.R;
import com.training.drcalory.Signup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.lang.String.valueOf;

public class Profile_Activity_main extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText birthday,height,weight,medical_condition;
    private Calendar myCalendar;
    private Spinner active;
    private Button submit;
    private RadioGroup radiogrp;
    private RadioButton gender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.activity_profile, container, false);

        myCalendar = Calendar.getInstance();
        birthday = (EditText) root.findViewById(R.id.etBirthday);
        active = (Spinner) root.findViewById(R.id.spinnerActive);
        submit = (Button) root.findViewById(R.id.btnSubmit);
        radiogrp = (RadioGroup) root.findViewById(R.id.radiogrpGender);
        height = (EditText) root.findViewById(R.id.etHeight);
        weight = (EditText) root.findViewById(R.id.etWeight);
        medical_condition = (EditText) root.findViewById(R.id.etSpecificMedical);

        active.setOnItemSelectedListener(this);

        List<String> activeElement = new ArrayList<String>();
        activeElement.add("Sedentary");
        activeElement.add("Lightly Active");
        activeElement.add("Moderately Active");
        activeElement.add("Active");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, activeElement);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        active.setAdapter(dataAdapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        SharedPreferences pref = getActivity().getSharedPreferences("Pref", 0);
        final String mail = pref.getString("sharedEmail", "null");

        final SharedPreferences profData = getActivity().getSharedPreferences("ProfData", 0);
        final SharedPreferences.Editor editor = profData.edit();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedGender = radiogrp.getCheckedRadioButtonId();
                gender = (RadioButton)root.findViewById(selectedGender);

                final String lifestyle = active.getSelectedItem().toString();

                Apihandler.getApiService().profile(mail,birthday.getText().toString(),gender.getText().toString(),
                        height.getText().toString(),weight.getText().toString(),lifestyle,medical_condition.getText().toString(),
                        new Callback<ProfileModel>()
                        {
                            @Override
                            public void success(ProfileModel profileModel, Response response) {

                                editor.clear();
                                editor.putString("shPrBd",birthday.getText().toString());
                                editor.putString("shPrGe",gender.getText().toString());
                                editor.putString("shPrHe",height.getText().toString());
                                editor.putString("shPrWe",weight.getText().toString());
                                editor.putString("shPrLi",lifestyle);
                                editor.putString("shPrMe",medical_condition.getText().toString());
                                editor.apply();


                                Toast.makeText(getActivity(),"Profile Updated",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getActivity(),"fail",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        birthday.setText(profData.getString("shPrBd", ""));

        height.setText(profData.getString("shPrHe", ""));
        weight.setText(profData.getString("shPrWe", ""));
        active.setSelection(activeElement.indexOf(profData.getString("shPrLi", "choose")));
        medical_condition.setText(profData.getString("shPrMe", ""));

        return root;

    }

    void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        birthday.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
