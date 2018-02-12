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
import android.widget.Toast;

import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.R;
import com.example.employeesattendance.utils.Utils;
import com.example.employeesattendance.employee.model.RegisterMainResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";
    private EditText edit_name, edit_last, edit_number, edit_email, edit_pass;
    private Button btn_signup;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_last = (EditText) findViewById(R.id.edit_last);
        edit_number = (EditText) findViewById(R.id.edit_number);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_pass = (EditText) findViewById(R.id.edit_pass);

        btn_signup = (Button) findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                if (edit_name.getText().toString().trim().equalsIgnoreCase("")){
                    edit_name.setError("Please Enter Your First Name");
                }else if (edit_last.getText().toString().trim().equalsIgnoreCase("")){
                    edit_last.setError("Please Enter Your Last Name");
                }else if (edit_number.getText().length() < 10){
                    edit_number.setError("Please Enter Your Mobile Number");
                }else if (edit_number.getText().length() == 0) {
                    edit_number.setError("Please Enter Your Mobile Number");
                }else if (edit_email.getText().length() == 0){
                    edit_email.setError("Please Enter Your Email Address ");
                }else if (!edit_email.getText().toString().matches(emailPattern)){
                    edit_email.setError("Please Insert Valid Email");
                }else if (edit_pass.getText().length() < 6){
                    edit_pass.setError("Must Insert More than six character");
                }else if (edit_pass.getText().length() == 0) {
                    edit_pass.setError("Must Insert Password");
                }else if (validatePassword(edit_pass.getText().toString())){
                    edit_pass.setError("Please Enter atleast one number");
                }else {
                    if (Utils.isConnectingToInternet(SignUpActivity.this)) {
                        signUp();
                    }else {
                        Toast.makeText(this, "No Internet connection...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public static boolean validatePassword(String pswd) {
        //check that there are letters
        if (!pswd.matches("[a-zA-Z]+")) {
            return false;
        } //nope no letters, stop checking and fail!
        //check if there are any numbers
        if (!pswd.matches("(?=.*?[0-9])")) {
            return false;
        }//nope no numbers, stop checking and fail!
        //check any valid special characters
        //everything has passed so far, lets return valid
        return true;
    }

    private void signUp() {
        pd = new ProgressDialog(SignUpActivity.this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("fname", edit_name.getText().toString());
        params.put("lname", edit_last.getText().toString());
        params.put("email",edit_email.getText().toString());
        params.put("password",edit_pass.getText().toString());
        params.put("mobile_no",edit_number.getText().toString());

        Log.e(TAG, "URL:" + Constant.BASE_URL + "signup.php?" + params);
        Log.e(TAG, params.toString());
        client.post(this, Constant.BASE_URL+"signup.php?",params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }
            @Override
            public void onFinish() {
                super.onFinish();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e(TAG, "RESPONSE-" + response);
                pd.dismiss();
                if (!(response == null)) {
                    RegisterMainResponse model = new Gson().fromJson(new String(String.valueOf(response)), RegisterMainResponse.class);
                    if (model.getStatus().equalsIgnoreCase("true")) {
                        Utils.WriteSharePrefrence(SignUpActivity.this, Constant.USERID, model.getData().getId());
                        String user_id = Utils.ReadSharePrefrence(SignUpActivity.this, Constant.USERID);
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        Toast.makeText(SignUpActivity.this, "Successfully Registed...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
                Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
