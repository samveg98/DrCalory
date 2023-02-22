package com.training.drcalory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginModel {

    @SerializedName("result1")
    @Expose

    private List<LoginResult> results =null;

    public List<LoginResult> getResults() {
        return results;
    }

    public void setResults(List<LoginResult> results) {
        this.results = results;
    }

}
