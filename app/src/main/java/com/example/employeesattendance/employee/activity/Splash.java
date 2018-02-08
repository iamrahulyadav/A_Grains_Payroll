package com.example.employeesattendance.employee.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.R;
import com.example.employeesattendance.utils.Utils;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private static final String TAG = Splash.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                if (!Utils.ReadSharePrefrence(Splash.this, Constant.USERID).equals("")){
                    Intent in = new Intent(Splash.this,EmployeeDashBoard.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                }else {
                    Intent in = new Intent(Splash.this,LoginActivity.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
