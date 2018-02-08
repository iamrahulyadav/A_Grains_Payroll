package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 8/2/18.
 */

public class GetOTPResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private GetOTP data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetOTP getData() {
        return data;
    }

    public void setData(GetOTP data) {
        this.data = data;
    }


}
