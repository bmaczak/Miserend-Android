package com.frama.miserend.hu.report;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Balazs on 2018. 03. 19..
 */

public class ReportProblemBody {

    @SerializedName("tid")
    private int churchId;
    @SerializedName("pid")
    private int problem;
    @SerializedName("text")
    private String text;
    @SerializedName("email")
    private String email;
    @SerializedName("dbdate")
    private String dbDate;

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }

    public int getProblem() {
        return problem;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDbDate() {
        return dbDate;
    }

    public void setDbDate(String dbDate) {
        this.dbDate = dbDate;
    }
}
