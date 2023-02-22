package com.training.drcalory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileModel {

    @SerializedName("result2")
    @Expose

    private List<SignupResult> result= null;

    public List<SignupResult>getResult() {
        return result;
    }

    public void setResult(List<SignupResult>result) {
        this.result = result;
    }
}
