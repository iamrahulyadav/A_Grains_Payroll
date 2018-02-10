package com.example.employeesattendance.employee.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 10/2/18.
 */

public class GetMonthlyreport {
    @SerializedName("id")
    private String id;

    @SerializedName("emp_id")
    private String emp_id;

    @SerializedName("date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getWorking_hour() {
        return working_hour;
    }

    public void setWorking_hour(String working_hour) {
        this.working_hour = working_hour;
    }

    public String getOver_time_hour() {
        return over_time_hour;
    }

    public void setOver_time_hour(String over_time_hour) {
        this.over_time_hour = over_time_hour;
    }

    public String getTotal_salary() {
        return total_salary;
    }

    public void setTotal_salary(String total_salary) {
        this.total_salary = total_salary;
    }

    @SerializedName("month_name")
    private String month_name;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("working_hour")
    private String working_hour;

    @SerializedName("over_time_hour")
    private String over_time_hour;

    @SerializedName("total_salary")
    private String total_salary;
}
