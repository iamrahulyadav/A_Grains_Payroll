package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 8/2/18.
 */

public class GetOTP {
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @SerializedName("otp")
    private String otp;
}
