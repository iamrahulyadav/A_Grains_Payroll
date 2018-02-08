package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 7/2/18.
 */

public class GetEditProfileResponse {
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

    public GetEditProfile getData() {
        return data;
    }

    public void setData(GetEditProfile data) {
        this.data = data;
    }

    @SerializedName("date")
    private GetEditProfile data;



}
