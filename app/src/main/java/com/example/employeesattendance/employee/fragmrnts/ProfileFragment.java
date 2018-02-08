package com.example.employeesattendance.employee.fragmrnts;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.employeesattendance.utils.Constant;
import com.example.employeesattendance.utils.ImageFilePath;
import com.example.employeesattendance.R;
import com.example.employeesattendance.utils.Utility;
import com.example.employeesattendance.utils.Utils;
import com.example.employeesattendance.employee.model.GetEditProfileResponse;
import com.example.employeesattendance.employee.model.GetProfileResponse;
import com.example.employeesattendance.employee.model.ProfilePicResponse;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "ProfileActivity";
    private ImageView img_profile_back, img_edit_number, img_edit_email;
    private CircleImageView img_profile_pic;
    private TextView txt_employee_name, txt_employee_num, txt_employee_email;
    private LinearLayout linear_number, linear_edit_number, linear_email, linear_edit_email;
    private EditText edt_num, edt_email;
    private Button btn_save_num, btn_save_email;
    private int SELECT_FILE = 1;
    private String imagePath;
    private ProgressDialog pd;
    private String getProfileImage;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        img_profile_pic = (CircleImageView) view.findViewById(R.id.img_profile_pic);
        img_edit_number = (ImageView) view.findViewById(R.id.img_edit_number);
        img_edit_email = (ImageView) view.findViewById(R.id.img_edit_email);

        txt_employee_name = (TextView) view.findViewById(R.id.txt_employee_name);
        txt_employee_num = (TextView) view.findViewById(R.id.txt_employee_num);
        txt_employee_email = (TextView) view.findViewById(R.id.txt_employee_email);

        edt_num = (EditText) view.findViewById(R.id.edt_num);
        edt_email = (EditText) view.findViewById(R.id.edt_email);

        btn_save_num = (Button) view.findViewById(R.id.btn_save_num);
        btn_save_email = (Button) view.findViewById(R.id.btn_save_email);

        linear_number = (LinearLayout) view.findViewById(R.id.linear_number);
        linear_edit_number = (LinearLayout) view.findViewById(R.id.linear_edit_number);
        linear_email = (LinearLayout) view.findViewById(R.id.linear_email);
        linear_edit_email = (LinearLayout) view.findViewById(R.id.linear_edit_email);

        img_profile_pic.setOnClickListener(this);
        img_edit_number.setOnClickListener(this);
        btn_save_num.setOnClickListener(this);
        img_edit_email.setOnClickListener(this);
        btn_save_email.setOnClickListener(this);

        getUserProfile();

        return view;
    }

    private void getUserProfile() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(), Constant.USERID));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "get_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL + "get_profile.php?", params, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                GetProfileResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetProfileResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")) {
                    txt_employee_name.setText(model.getData().getFirst_name()+" "+model.getData().getLast_name());
                    Utils.WriteSharePrefrence(getActivity(),Constant.USER_NAME,model.getData().getFirst_name()+" "+model.getData().getLast_name());
                    String user_name = Utils.ReadSharePrefrence(getActivity(),Constant.USER_NAME);
                    txt_employee_num.setText(model.getData().getMobile_no());
                    Utils.WriteSharePrefrence(getActivity(),Constant.PHONE_NUM,model.getData().getMobile_no());
                    String phone_num = Utils.ReadSharePrefrence(getActivity(),Constant.PHONE_NUM);
                    txt_employee_email.setText(model.getData().getEmail());
                    Utils.WriteSharePrefrence(getActivity(),Constant.EMAIL,model.getData().getEmail());
                    String email_id = Utils.ReadSharePrefrence(getActivity(),Constant.EMAIL);
                    if (model.getData().getImage().isEmpty()) {
                        Picasso.with(getActivity()).load(R.drawable.ic_prifile_blue);
                    } else {
                        Picasso.with(getActivity()).load(model.getData().getImage()).placeholder(R.drawable.ic_prifile_blue).into(img_profile_pic);
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_edit_number:
                linear_number.setVisibility(View.GONE);
                linear_edit_number.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save_num:
                editphonenumber();
                break;
            case R.id.img_edit_email:
                linear_email.setVisibility(View.GONE);
                linear_edit_email.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_save_email:
                editemail();
                break;
            case R.id.img_profile_pic:
                SelectProfile();
                break;
        }
    }

    private void editemail() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(), Constant.USERID));
        params.put("email",edt_email.getText().toString());
        params.put("mobile_no",Utils.ReadSharePrefrence(getActivity(),Constant.PHONE_NUM));

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL + "edit_profile.php?", params, new JsonHttpResponseHandler() {
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
                GetEditProfileResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetEditProfileResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")) {
                    linear_edit_email.setVisibility(View.GONE);
                    linear_email.setVisibility(View.VISIBLE);
                    txt_employee_email.setText(model.getData().getEmail());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void editphonenumber() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(), Constant.USERID));
        params.put("email",Utils.ReadSharePrefrence(getActivity(),Constant.EMAIL));
        params.put("mobile_no",edt_num.getText().toString());

        Log.e(TAG, "USERURL:" + Constant.BASE_URL + "edit_profile.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL + "edit_profile.php?", params, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Driver RESPONSE-" + response);
                GetEditProfileResponse model = new Gson().fromJson(new String(String.valueOf(response)), GetEditProfileResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")) {
                    linear_edit_number.setVisibility(View.GONE);
                    linear_number.setVisibility(View.VISIBLE);
                    txt_employee_num.setText(model.getData().getMobile_no());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void SelectProfile() {
        boolean result = Utility.checkPermission(getActivity());
        if (result) {
            galleryIntent();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                if (data != null) {
                    Uri imageUri = data.getData();
                    imagePath = ImageFilePath.getPath(getActivity(), data.getData());
                    editProfilePic();
                }
            }
        }
    }

    private void editProfilePic() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(Constant.USERNAME, Constant.PASSWORD);
        RequestParams params = new RequestParams();
        File file1 = new File(imagePath);
        params.put("user_id", Utils.ReadSharePrefrence(getActivity(), Constant.USERID));
        try {
            params.put("file", file1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "ProfileUrl:" + Constant.BASE_URL + "change_profile_picture.php?" + params);
        Log.e(TAG, params.toString());
        client.post(getActivity(), Constant.BASE_URL + "change_profile_picture.php?", params, new JsonHttpResponseHandler() {
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
                Log.e(TAG, "Profile RESPONSE-" + response);
                ProfilePicResponse model = new Gson().fromJson(new String(String.valueOf(response)), ProfilePicResponse.class);
                pd.dismiss();
                if (model.getStatus().equalsIgnoreCase("true")){
                    if (model.getData().getImage().isEmpty()) {
                        Picasso.with(getActivity()).load(R.drawable.ic_prifile_blue);
                    } else {
                        Picasso.with(getActivity()).load(model.getData().getImage()).placeholder(R.drawable.ic_prifile_blue).into(img_profile_pic);
                    }
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(TAG, throwable.getMessage());
            }
        });
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        img_profile_pic.setImageBitmap(bm);

    }


}
