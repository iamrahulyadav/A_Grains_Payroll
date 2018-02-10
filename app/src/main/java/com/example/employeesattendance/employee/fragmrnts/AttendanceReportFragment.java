package com.example.employeesattendance.employee.fragmrnts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
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
public class AttendanceReportFragment extends Fragment implements View.OnClickListener {

    public Toolbar toolbar;
    private ImageView img_calendar_privaous, img_calendar_next;
    private TextView txt_calendar_monthname;
    private CompactCalendarView compactcalendar_view;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());

    public AttendanceReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_report, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.admin_toolbar);

        img_calendar_privaous = view.findViewById(R.id.img_calendar_privaous);
        img_calendar_next = view.findViewById(R.id.img_calendar_next);

        txt_calendar_monthname = view.findViewById(R.id.txt_calendar_monthname);

        compactcalendar_view = view.findViewById(R.id.compactcalendar_view);

        img_calendar_next.setOnClickListener(this);
        img_calendar_privaous.setOnClickListener(this);
        txt_calendar_monthname.setText(dateFormatForMonth.format(compactcalendar_view.getFirstDayOfCurrentMonth()));

        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txt_calendar_monthname.setText(dateFormatForMonth.format(compactcalendar_view.getFirstDayOfCurrentMonth()));
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_calendar_privaous:
                compactcalendar_view.showPreviousMonth();
                break;
            case R.id.img_calendar_next:
                compactcalendar_view.showNextMonth();
                break;
        }
    }

}
