package com.example.employeesattendance.employee.fragmrnts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

    private RecyclerView recycler_view_month;

    public SalaryReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salary_report, container, false);

        recycler_view_month = view.findViewById(R.id.recycler_view_month);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
