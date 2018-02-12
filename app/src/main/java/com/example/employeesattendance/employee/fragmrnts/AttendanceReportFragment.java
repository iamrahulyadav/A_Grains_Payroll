package com.example.employeesattendance.employee.fragmrnts;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeesattendance.R;
import com.example.employeesattendance.employee.activity.EmployeeDashBoard;
import com.example.employeesattendance.employee.activity.LoginActivity;
import com.example.employeesattendance.employee.model.LoginMainResponse;
import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.utils.Utils;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceReportFragment extends Fragment{

    public Toolbar toolbar;
    private TextView txt_calendar_monthname;
    private CompactCalendarView compactcalendar_view;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private ProgressDialog pd;
    private TextView tvWorking, tvOvertime;

    public AttendanceReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_report, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.admin_toolbar);

        txt_calendar_monthname = view.findViewById(R.id.txt_calendar_monthname);

        compactcalendar_view = view.findViewById(R.id.compactcalendar_view);

        tvWorking = view.findViewById(R.id.att_tvWorkingHours);
        tvOvertime = view.findViewById(R.id.att_tvOvertimeHours);

        txt_calendar_monthname.setText(dateFormatForMonth.format(compactcalendar_view.getFirstDayOfCurrentMonth()));

        compactcalendar_view.shouldScrollMonth(false);
        compactcalendar_view.displayOtherMonthDays(false);

        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String date = sdf.format(dateClicked);

                getReport(date);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
            }
        });

        return view;
    }

    private void getReport(String date) {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("user_id",Utils.ReadSharePrefrence(getActivity(), Constant.USERID));
        params.put("date",date);

        Log.e("", "URL:" + Constant.BASE_URL + "attendance_report.php?" + params);
        Log.e("", params.toString());
        client.post(getActivity(), Constant.BASE_URL+"attendance_report.php?",params, new JsonHttpResponseHandler() {
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
                Log.e("", "LOGIN RESPONSE-" + response);
                pd.dismiss();
                LoginMainResponse model =new Gson().fromJson(String.valueOf(response),LoginMainResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {

                    try {

                        tvWorking.setText(response.getJSONObject("data").getString("working_hour") + " hour");
                        tvOvertime.setText(response.getJSONObject("data").getString("over_time_hour") + " hour");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                     tvWorking.setText("0 hour");
                    tvOvertime.setText("0 hour");

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("", throwable.getMessage());
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
