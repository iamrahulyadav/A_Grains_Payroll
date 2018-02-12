package com.example.employeesattendance.employee.fragmrnts;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeesattendance.R;
import com.example.employeesattendance.employee.model.LoginMainResponse;
import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment implements LocationListener {

    private static final String TAG = "LocationFragment";
    private static final int REQUEST_CODE = 1001;
    public Criteria criteria;
    public String bestProvider;
    Date date;
    SimpleDateFormat simpleDateFormat;
    SimpleDateFormat simpleDateFormat1;
    String time, inTime, lat, lng;
    Location location;
    private TextView txt_currentdate, txt_InIime, txt_OutTime, workinghour, overtimehour;
    private Button btn_InTime, btn_OutTime;
    private ProgressDialog pd;
    private LocationManager locationManager;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_currentdate = view.findViewById(R.id.att_tvDate);
        txt_InIime = view.findViewById(R.id.att_tvInTime);
        txt_OutTime = view.findViewById(R.id.att_tvOutTime);
        btn_InTime = view.findViewById(R.id.att_btnInTime);
        btn_OutTime = view.findViewById(R.id.att_btnOutTime);
        workinghour = view.findViewById(R.id.att_tvWorkingTime);
        overtimehour = view.findViewById(R.id.att_tvOverTime);

        date = new Date();  // to get the date
        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy"); // getting date in this format
        String formattedDate = df.format(date.getTime());
        txt_currentdate.setText(formattedDate);

        simpleDateFormat = new SimpleDateFormat("hh:mm a");
        time = simpleDateFormat.format(date.getTime());

        simpleDateFormat1 = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        inTime = simpleDateFormat1.format(date.getTime());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            checkLocationPermission();
//            // Do things the Lollipop way
//        } else {
//            // Do things the pre-Lollipop way
//        }
//
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE);

            return;
        }
//
//        getLocation();
//
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            onLocationChanged(location);
//        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            getActivity().finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        btn_InTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPunching("in");
            }
        });

        btn_OutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPunching("out");
            }
        });
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getActivity());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result, 0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            // Received permission result for camera permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLocationChanged(location);
            } else {
                Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setPunching(final String status) {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("latitude", lat);
        params.put("longitude", lng);
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(), Constant.USERID));
        params.put("time", inTime);

        Log.e("", "URL:" + Constant.BASE_URL + "attendence.php?" + params);
        Log.e("", params.toString());
        client.post(getActivity(), Constant.BASE_URL + "attendence.php?", params, new JsonHttpResponseHandler() {
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
                LoginMainResponse model = new Gson().fromJson(String.valueOf(response), LoginMainResponse.class);
                if (model.getStatus().equalsIgnoreCase("true")) {

                    if (status.equals("in")) {

                        txt_InIime.setText(time);
                        try {
                            Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (status.equals("out")) {
                        txt_OutTime.setText(time);
                        try {
                            workinghour.setText(response.getJSONObject("data").getString("working_hour") + " hr");
                            overtimehour.setText(response.getJSONObject("data").getString("over_time_hour") + " hr");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    try {
                        Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("", throwable.getMessage());
            }
        });
    }

//    protected void getLocation() {
//        if (isLocationEnabled(getActivity())) {
//            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//            criteria = new Criteria();
//            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));
//
//            //You can still do this if you like, you might get lucky:
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                        REQUEST_CODE);
//                return;
//            }
//            Location location = locationManager.getLastKnownLocation(bestProvider);
//            if (location != null) {
//                Log.e("TAG", "GPS is on");
//                lat = String.valueOf(location.getLatitude());
//                lng = String.valueOf(location.getLongitude());
//                Toast.makeText(getActivity(), "latitude:" + lat + " longitude:" + lng, Toast.LENGTH_SHORT).show();
//            } else {
//                //This is what you need:
//                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
//            }
//        } else {
//            //prompt user to enable location....
//            //.................
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        locationManager.removeUpdates(this);
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        //Hey, a non null location! Sweet!
//
//        //remove location callback:
//        locationManager.removeUpdates(this);
//
//        //open the map:
//        lat = String.valueOf(location.getLatitude());
//        lng = String.valueOf(location.getLongitude());
//        Toast.makeText(getActivity(), "latitude:" + lat + " longitude:" + lng, Toast.LENGTH_SHORT).show();
//
//    }

    @Override
    public void onLocationChanged(Location location) {

        if(isLocationEnabled(getActivity()) && location != null) {

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
