package com.example.employeesattendance.employee.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.employeesattendance.R;
import com.example.employeesattendance.employee.model.ChangePasswordResponse;
import com.example.employeesattendance.employee.model.GetOTPResponse;
import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class ForgotpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ForgotpasswordActivity";
    private LinearLayout linear_enter_email, linear_set_otp, linear_change_pass;
    private EditText edit_email_for_pass, edit_otp, edit_change_pass;
    private Button btn_forgotpassword, btn_set_otp, btn_change_pass;
    private ProgressDialog pd;
    private String otp_str;

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
                if (edit_email_for_pass.getText().toString().isEmpty()){
                    edit_email_for_pass.setError("Enter Your Email");
                }else {
                    sendOtp();
                }
                break;
            case R.id.btn_set_otp:
                otp_str = edit_otp.getText().toString();
                if (edit_otp.getText().toString().equals(Utils.ReadSharePrefrence(ForgotpasswordActivity.this, Constant.OTP))) {
                    linear_enter_email.setVisibility(View.GONE);
                    linear_set_otp.setVisibility(View.GONE);
                    linear_change_pass.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(this, "Your Otp Is Invalid", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_change_pass:
                if (edit_change_pass.getText().toString().isEmpty()){
                    edit_change_pass.setError("Enter password");
                }else {
                    changepassword();
                }
                break;
        }
    }

    private void changepassword() {
        pd = new ProgressDialog(ForgotpasswordActivity.this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("otp",otp_str);
        params.put("new_password",edit_change_pass.getText().toString());
        Log.e(TAG, "URL:" + Constant.BASE_URL + "reset_password.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"reset_password.php?",params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                pd.dismiss();
                ChangePasswordResponse model =new Gson().fromJson(new String(String.valueOf(response)),ChangePasswordResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    startActivity(new Intent(ForgotpasswordActivity.this,LoginActivity.class));
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void sendOtp() {
        pd = new ProgressDialog(ForgotpasswordActivity.this);
        pd.setMessage("Otp code is sent on your email");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("email",edit_email_for_pass.getText().toString());
        Log.e(TAG, "URL:" + Constant.BASE_URL + "forgot_password.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"forgot_password.php?",params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {

                super.onFinish();
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "LOGIN RESPONSE-" + response);
                pd.dismiss();
                GetOTPResponse model =new Gson().fromJson(new String(String.valueOf(response)),GetOTPResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {
                    linear_enter_email.setVisibility(View.GONE);
                    linear_set_otp.setVisibility(View.VISIBLE);
                    Utils.WriteSharePrefrence(ForgotpasswordActivity.this,Constant.OTP,model.getData().getOtp());
                    String otp = Utils.ReadSharePrefrence(ForgotpasswordActivity.this,Constant.OTP);
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
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
