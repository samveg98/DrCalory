package com.training.drcalory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class askModel {

    //Models are for sending and receiving the response from the php file
    //the response we get will be stored in result

    @SerializedName("result")
    @Expose

    private List<getDailyResult> results =null;

    public List<getDailyResult> getResults() {
        return results;
    }

    public void setResults(List<getDailyResult> results) {
        this.results = results;
    }
}
