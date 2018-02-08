package com.example.employeesattendance.employee.fragmrnts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.employeesattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceReportFragment extends Fragment implements View.OnClickListener {

    public Toolbar toolbar;


    public AttendanceReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attendance_report, container, false);

        toolbar = (Toolbar) getActivity().findViewById(R.id.admin_toolbar);



        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
