package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 10/2/18.
 */

public class MonthlyReportResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GetMonthlyreport getData() {
        return data;
    }

    public void setData(GetMonthlyreport data) {
        this.data = data;
    }

    @SerializedName("data")
    private GetMonthlyreport data;
}
