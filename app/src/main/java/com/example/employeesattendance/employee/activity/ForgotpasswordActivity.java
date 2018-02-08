package com.example.employeesattendance.employee.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.employeesattendance.R;

public class ForgotpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linear_enter_email, linear_set_otp, linear_change_pass;
    private EditText edit_email_for_pass, edit_otp, edit_change_pass;
    private Button btn_forgotpassword, btn_set_otp, btn_change_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        linear_enter_email = (LinearLayout) findViewById(R.id.linear_enter_email);
        linear_set_otp = (LinearLayout) findViewById(R.id.linear_set_otp);
        linear_change_pass = (LinearLayout) findViewById(R.id.linear_change_pass);

        edit_email_for_pass = (EditText) findViewById(R.id.edit_email_for_pass);
        edit_otp = (EditText) findViewById(R.id.edit_otp);
        edit_change_pass = (EditText) findViewById(R.id.edit_change_pass);

        edit_email_for_pass.addTextChangedListener(new MyTextWatcher(edit_email_for_pass));
        edit_otp.addTextChangedListener(new MyTextWatcher(edit_otp));
        edit_change_pass.addTextChangedListener(new MyTextWatcher(edit_change_pass));

        btn_forgotpassword = (Button) findViewById(R.id.btn_forgotpassword);
        btn_set_otp = (Button) findViewById(R.id.btn_set_otp);
        btn_change_pass = (Button) findViewById(R.id.btn_change_pass);

        btn_forgotpassword.setOnClickListener(this);
        btn_set_otp.setOnClickListener(this);
        btn_change_pass.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forgotpassword:
                linear_enter_email.setVisibility(View.GONE);
                linear_set_otp.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_set_otp:
                linear_enter_email.setVisibility(View.GONE);
                linear_set_otp.setVisibility(View.GONE);
                linear_change_pass.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_change_pass:
                startActivity(new Intent(ForgotpasswordActivity.this,LoginActivity.class));
                break;
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edit_email_for_pass:
                    break;
                case R.id.edit_otp:
                    break;
                case R.id.edit_change_pass:
                    break;
            }
        }
    }

}
