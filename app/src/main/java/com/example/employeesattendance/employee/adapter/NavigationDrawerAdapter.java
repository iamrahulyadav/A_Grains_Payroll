package com.example.employeesattendance.employee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.employeesattendance.R;

/**
 * Created by archirayan on 8/2/18.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationHolder> {

    public Context context;

    public NavigationDrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NavigationDrawerAdapter.NavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_item, parent,false);
        NavigationHolder viewHolder = new NavigationHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.NavigationHolder holder, int position) {
        holder.nameTv.setText(context.getResources().getStringArray(R.array.navigation_item)[position]);

    }


    @Override
    public int getItemCount() {
        return context.getResources().getStringArray(R.array.navigation_item).length;
    }

    public class NavigationHolder extends RecyclerView.ViewHolder {
        public TextView nameTv;
        public NavigationHolder(View itemView) {
            super(itemView);

            nameTv = (TextView) itemView.findViewById(R.id.navigation_view_name1);
        }
    }
}
