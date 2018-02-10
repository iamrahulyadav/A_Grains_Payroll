package com.example.employeesattendance.employee.fragmrnts;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeesattendance.R;
import com.example.employeesattendance.employee.model.MonthlyReportResponse;
import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalaryReportFragment extends Fragment {

    private static final String TAG = "SalaryReportFragment";
    private ImageView img_back_report;
    private TextView txt_employeename, txt_privuousmonth, txt_totalhours, txt_overtimehours, txt_totalsalary;
    private ProgressDialog pd;

    public SalaryReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salary_report, container, false);

        txt_employeename = view.findViewById(R.id.txt_employeename);
        txt_privuousmonth = view.findViewById(R.id.txt_privuousmonth);
        txt_totalhours = view.findViewById(R.id.txt_totalhours);
        txt_overtimehours = view.findViewById(R.id.txt_overtimehours);
        txt_totalsalary = view.findViewById(R.id.txt_totalsalary);

        if (Utils.isConnectingToInternet(getActivity())) {
            getMonthlyReport();
        }else {
            Toast.makeText(getActivity(), "No Internet connection...", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void getMonthlyReport() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(),Constant.USERID));

        Log.e(TAG, "URL:" + Constant.BASE_URL + "monthly_report.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL+"monthly_report.php?",params, new JsonHttpResponseHandler() {
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
                    MonthlyReportResponse model = new Gson().fromJson(new String(String.valueOf(response)), MonthlyReportResponse.class);
                    if (model.getStatus().equalsIgnoreCase("true")) {
                        txt_employeename.setText(model.getData().getFirst_name());
                        txt_privuousmonth.setText(model.getData().getDate());
                        txt_totalhours.setText(model.getData().getWorking_hour());
                        txt_overtimehours.setText(model.getData().getOver_time_hour());
                        txt_totalsalary.setText(model.getData().getTotal_salary());
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

}
