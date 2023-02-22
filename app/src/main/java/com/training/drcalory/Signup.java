package com.training.drcalory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UCharacterEnums;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.training.drcalory.ApiHandler.Apihandler;
import com.training.drcalory.Model.SignupModel;
import com.training.drcalory.Model.SignupResult;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class Signup extends AppCompatActivity {
    TextView tvLogin;
    EditText fname1, lname1, email1, pwd, repswd;
    Button signup;
    String fname = null,lname=null,email=null,password=null;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvLogin = (TextView) findViewById(R.id.tvLogin);

        fname1 = (EditText) findViewById(R.id.fname);
        lname1 = (EditText) findViewById(R.id.lname);
        email1 = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.password);
        repswd = (EditText) findViewById(R.id.etRPassword);
        signup = (Button) findViewById(R.id.signup);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("Pref", 0);
        final SharedPreferences.Editor editor = pref.edit();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validSuccess = true;
                fname = fname1.getText().toString();
                lname = lname1.getText().toString();
                email = email1.getText().toString();
                password = pwd.getText().toString();

                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                    validSuccess = false;
                }

                else if (fname.equals("")) {
                    fname1.setError("Enter First Name");
                    validSuccess = false;
                }
                //name validation
                //name will only contain alphabates
                else if (! fname.matches("^[a-zA-Z]*$")){
                    fname1.setError("Only Alphabets allowed");
                    validSuccess = false;
                }
                else if (lname.equals("")) {
                    lname1.setError("Enter Last Name");
                    validSuccess = false;
                }
                else if (! lname.matches("^[a-zA-Z]*$")){
                    lname1.setError("Only Alphabets allowed");
                    validSuccess = false;
                }
                else if (email.equals("")) {
                    email1.setError("Enter Email");
                    validSuccess = false;
                }
                //email validation
                //comparing with email validation regular expression, so that we get a valid user email
                else if (! email.matches("^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")){
                    email1.setError("Enter valid Email");
                    validSuccess = false;
                }
                else if (password.equals("")) {
                    pwd.setError("Enter Password");
                    validSuccess = false;
                }
                else if (repswd.getText().toString().isEmpty()) {
                    repswd.setError("Enter Password again");
                    validSuccess = false;
                }
                //checking if password and reenter password value are same or not
                else if (!repswd.getText().toString().matches(password)) {
                    repswd.setError("Enter correct Password");
                    validSuccess = false;
                }
                else if (validSuccess == true) {

                    Apihandler.getApiService().signup(email, password,fname, lname, new Callback<SignupModel>() {
                        @Override
                        public void success(SignupModel signupModel, Response response) {

                            String value = new String(((TypedByteArray) response.getBody()).getBytes());

                            //if there is already a user with the same email, this error will be shown
                            if(value.contains("user already exist"))
                            {
                                Toast.makeText(Signup.this, "User Already Exist" , Toast.LENGTH_LONG).show();
                            }
                            else if(value.contains("failed"))
                            {
                                Toast.makeText(Signup.this, "Signup Failed" , Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(Signup.this, "Signup Successful", Toast.LENGTH_SHORT).show();

                                editor.clear();
                                //storing the value of email in the shared preference
                                editor.putString("sharedEmail",email);
                                editor.apply();

                                Intent i = new Intent(Signup.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }

                        }


                        //if the Api handler request fails, it will go in the failure method
                        @Override
                        public void failure(RetrofitError error) {
                            String er = error.toString();
                            Log.d("<>", er);
                            Toast.makeText(Signup.this, "Signup failed:" + er, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent l = new Intent(Signup.this, Login.class);
                startActivity(l);
                Signup.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }
}


