package com.frama.miserend.hu.report.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Balazs on 2018. 03. 19..
 */

public class ReportProblemResponse {

    @SerializedName("error")
    private int error;
    @SerializedName("text")
    private String text;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
