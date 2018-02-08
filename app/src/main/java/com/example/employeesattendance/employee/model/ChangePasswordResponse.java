package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 8/2/18.
 */

public class ChangePasswordResponse {
    @SerializedName("status")
    private String status;

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

    @SerializedName("msg")
    private String msg;

}
