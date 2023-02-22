package com.training.drcalory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.training.drcalory.ApiHandler.Apihandler;
import com.training.drcalory.Model.LoginModel;

import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class Login extends AppCompatActivity {
    TextView tvSignup;
    EditText etEmail,etPassword;
    Button login;
    String mail= null,pass = null,pswd =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignup =(TextView) findViewById(R.id.tvSignup);
        etEmail =(EditText) findViewById(R.id.etEmail);
        etPassword =(EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.login);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Pref", 0);
        final Editor editor = pref.edit();


        //On login page, we are also giving an option for signup
        //so if user click, user will be directed to signup page
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Signup.class);
                startActivity(i);
                Login.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mail = etEmail.getText().toString();
                pass = etPassword.getText().toString();

                boolean loginsuccess = true;

                //connection manager to connect to database
                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
                    Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
                    loginsuccess = false;
                }

                //validations for each user input
                //email validation that field should not be empty
                else if(mail.equals(""))
                {
                    etEmail.setError("Enter Email");
                    loginsuccess = false;
                }
                //
                else if(pass.equals(""))
                {
                    etPassword.setError("Enter Password");
                    loginsuccess=false;
                }

                else if (loginsuccess == true){

                    //apiHandler to send api and get response
                    Apihandler.getApiService().login(mail,pass, new Callback<LoginModel>() {
                        @Override
                        public void success(LoginModel loginModel, Response response) {
                            String value = new String(((TypedByteArray) response.getBody()).getBytes());

                            if(value.contains("success")){
                                Toast.makeText(Login.this,"Successful",Toast.LENGTH_SHORT).show();

                                editor.clear();
                                editor.putString("sharedEmail",mail);
                                editor.apply();

                                Intent i = new Intent(Login.this,MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                            else if(value.contains("incorrect")){
                                etPassword.setError("Incorrect Password");
                                Toast.makeText(Login.this,"Password Incorrect",Toast.LENGTH_LONG).show();
                            }
                            //in login, if email entered, the user does not exist in database..
                            //then it will display user not found
                            else if(value.contains("not found")){
                                etEmail.setError("User Not Found");
                                Toast.makeText(Login.this,"User Not Found",Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void failure(RetrofitError error) {
                            String er = error.toString();
                            Toast.makeText(Login.this, "Failed" + er, Toast.LENGTH_LONG).show();
                            Log.d("<>","in fail");
                        }
                    });
                }

            }
        });

       // getSupportActionBar().hide();
    }
}
