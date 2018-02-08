package com.example.employeesattendance.employee.fragmrnts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeesattendance.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalaryReportFragment extends Fragment implements View.OnClickListener {

    private ImageView activity_calendar_previousmonth, activity_calendar_nextmonth, img_salary_back;
    private TextView activity_calendar_monthname;
    private CompactCalendarView admin_compactcalendar_view;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());


    public SalaryReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salary_report, container, false);

        activity_calendar_previousmonth = (ImageView) view.findViewById(R.id.activity_calendar_previousmonth);
        activity_calendar_nextmonth = (ImageView) view.findViewById(R.id.activity_calendar_nextmonth);
        admin_compactcalendar_view = (CompactCalendarView) view.findViewById(R.id.admin_compactcalendar_view);

        activity_calendar_previousmonth.setOnClickListener(this);
        activity_calendar_nextmonth.setOnClickListener(this);
        activity_calendar_monthname = (TextView) view.findViewById(R.id.activity_calendar_monthname);
        activity_calendar_monthname.setText(dateFormatForMonth.format(admin_compactcalendar_view.getFirstDayOfCurrentMonth()));

        admin_compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                activity_calendar_monthname.setText(dateFormatForMonth.format(admin_compactcalendar_view.getFirstDayOfCurrentMonth()));
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_calendar_previousmonth:
                admin_compactcalendar_view.showPreviousMonth();
                break;
            case R.id.activity_calendar_nextmonth:
                admin_compactcalendar_view.showNextMonth();
                break;
        }

    }
}
