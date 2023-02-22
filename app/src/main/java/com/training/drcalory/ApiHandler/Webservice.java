package com.training.drcalory.ApiHandler;

import android.text.Editable;

import com.training.drcalory.Model.LoginModel;
import com.training.drcalory.Model.ProfileModel;
import com.training.drcalory.Model.SignupModel;
import com.training.drcalory.Model.askModel;
import com.training.drcalory.Model.getDailyModel;
import com.training.drcalory.Model.getReplyModel;


import java.util.List;

import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.Callback;

public interface Webservice {

    //signup.php will be called and executed
    //the response will be received in Callback
    @POST("/signup.php")
    public void signup(@Query("email") String email,
                       @Query("password") String password,
                       @Query("fname") String fname,
                       @Query("lname") String lname,
                       Callback<SignupModel> callback);

    @POST("/login.php")
    public void login(@Query("mail") String mail,
                      @Query("pass") String pass,
                      Callback<LoginModel> callback);

    @POST("/profile.php")
    public void profile(@Query("email") String mail,
                        @Query("birthday") String birthday,
                        @Query("gender") String gender,
                        @Query("height") String height,
                        @Query("weight") String weight,
                        @Query("lifestyle") String lifestyle,
                        @Query("medical_condition") String medical_condition,
                        Callback<ProfileModel> callback);

    @POST("/getDaily.php")
    public void getDaily(@Query("mail") String mail,
                         Callback<LoginModel> callback);

    @POST("/getReply.php")
    public void getReply(@Query("mail") String mail,
                         Callback<getReplyModel> callback);

    @POST("/ask.php")
    public void ask(@Query("mail") String mail,
                    @Query("question") String question,
                    Callback<askModel> callback);

}
