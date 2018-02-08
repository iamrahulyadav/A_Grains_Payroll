package com.example.employeesattendance.employee.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeesattendance.utils.ClickListener;
import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.R;
import com.example.employeesattendance.utils.RecyclerTouchListener;
import com.example.employeesattendance.utils.Utils;
import com.example.employeesattendance.employee.adapter.NavigationDrawerAdapter;
import com.example.employeesattendance.employee.fragmrnts.AttendanceReportFragment;
import com.example.employeesattendance.employee.fragmrnts.HelpFragment;
import com.example.employeesattendance.employee.fragmrnts.HomeFragment;
import com.example.employeesattendance.employee.fragmrnts.ProfileFragment;
import com.example.employeesattendance.employee.fragmrnts.SalaryReportFragment;

public class EmployeeDashBoard extends AppCompatActivity  {

    public ImageView toolbar_icon;
    private DrawerLayout drawer;
    public LinearLayoutManager layoutManager;
    public Toolbar toolbar;
    private TextView toolbar_title2;
    public ImageView admin_toolbar_profile;
    public RecyclerView recyclerView;
    private TextView txt_home,txt_profilFirstname, txt_profilLastname, txt_attendance, txt_salary_report, txt_help, txt_logout,txt_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_dash_board);
        toolbar = (Toolbar) findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        toolbar_icon = (ImageView) findViewById(R.id.admin_toolbar_icon);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar_title2 = (TextView) findViewById(R.id.admin_toolbar_title);
        toolbar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawer.isDrawerOpen(Gravity.START))
                    drawer.openDrawer(Gravity.START);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        openDefaultFragment();

        recyclerView = (RecyclerView) navigationView.findViewById(R.id.lst_menu_items);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new NavigationDrawerAdapter(EmployeeDashBoard.this));
        // TODO: 15-Feb-17 Rujul Recycler View on Click
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(EmployeeDashBoard.this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Fragment fragment = null;
                // TODO: 15-Feb-17 Rujul On Click of item
                switch (position) {
                    case 0:
                        fragment = new HomeFragment();
                        toolbar_title2.setText("Home");
                        break;
                    case 1:
                        fragment = new ProfileFragment();
                        toolbar_title2.setText("Profile");
                        break;
                    case 2:
                        fragment = new AttendanceReportFragment();
                        toolbar_title2.setText("Attendance Report");
                        break;
                    case 3:
                        fragment = new SalaryReportFragment();
                        toolbar_title2.setText("Salary Report");
                        break;
                    case 4:
                        fragment = new HelpFragment();
                        toolbar_title2.setText("Help");
                        break;
                    case 5:
                        Utils.WriteSharePrefrence(EmployeeDashBoard.this, Constant.USERID,"");
                        Intent intent = new Intent(EmployeeDashBoard.this,LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    default:
                        break;
                }
                if (fragment != null) {
                    supportInvalidateOptionsMenu();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_admin_main, fragment).commit();
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        }));


    }

    private void openDefaultFragment() {
        Fragment fragment = null;
        fragment = new HomeFragment();
        if (fragment != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_admin_main, fragment).commit();
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    }

