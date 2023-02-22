package com.training.drcalory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getReplyModel {

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
