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
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.R;
import com.example.employeesattendance.utils.Utils;
import com.example.employeesattendance.employee.model.LoginMainResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private TextView txt_signupactivity;
    private EditText edit_email_login, edit_pass_login;
    private Button btn_signin, btn_Dforgot_pass;
    private ProgressDialog pd;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_signupactivity = (TextView) findViewById(R.id.txt_signupactivity);

        edit_email_login = (EditText) findViewById(R.id.edit_email_login);
        edit_pass_login = (EditText) findViewById(R.id.edit_pass_login);

        edit_email_login.addTextChangedListener(new MyTextWatcher(edit_email_login));
        edit_pass_login.addTextChangedListener(new MyTextWatcher(edit_pass_login));


        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_Dforgot_pass = (Button) findViewById(R.id.btn_Dforgot_pass);

        txt_signupactivity.setOnClickListener(this);
        btn_signin.setOnClickListener(this);
        btn_Dforgot_pass.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_signupactivity:
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                break;
            case R.id.btn_Dforgot_pass:
                startActivity(new Intent(LoginActivity.this,ForgotpasswordActivity.class));
                break;
            case R.id.btn_signin:
                if (edit_email_login.getText().length() == 0){
                    edit_email_login.setError("Enter Email Address");
                }else if (!edit_email_login.getText().toString().matches(emailPattern)) {
                    edit_email_login.setError("Please Insert Valid Email");
                }else if (edit_pass_login.getText().toString().isEmpty()){
                    edit_pass_login.setError("Pleas Enter PassWord");
                }else{
                    if (Utils.isConnectingToInternet(LoginActivity.this)) {
                        SignIn();
                    }else {
                        Toast.makeText(this, "No Internet connection...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void SignIn() {
        pd = new ProgressDialog(LoginActivity.this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("email",edit_email_login.getText().toString());
        params.put("password",edit_pass_login.getText().toString());

        Log.e(TAG, "URL:" + Constant.BASE_URL + "login.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"login.php?",params, new JsonHttpResponseHandler() {
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
                if (!(response == null)) {
                    LoginMainResponse model = new Gson().fromJson(new String(String.valueOf(response)), LoginMainResponse.class);
                    if (model.getStatus().equalsIgnoreCase("true")) {
                        Utils.WriteSharePrefrence(LoginActivity.this, Constant.USERID, model.getData().getId());
                        String Userid = Utils.ReadSharePrefrence(LoginActivity.this, Constant.USERID);
                        startActivity(new Intent(LoginActivity.this, EmployeeDashBoard.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "InValid Id and Password", Toast.LENGTH_SHORT).show();
                    }
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
                case R.id.edit_email_login:
                    break;
                case R.id.edit_pass_login:
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
