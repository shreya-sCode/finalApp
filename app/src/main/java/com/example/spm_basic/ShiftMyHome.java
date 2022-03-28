package com.example.spm_basic;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ShiftMyHome extends AppCompatActivity {

    EditText shiftDate, etname, et_your_mobile, et_pickupAdd, et_dropAdd;
    String dropdownRoomSize;
    ProgressBar pg_smh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_my_home);

        pg_smh = findViewById(R.id.pb_smh);
        etname = findViewById(R.id.et_your_name);


        //code for spinner dropdown
        final Spinner spinnerDropdown = (Spinner)findViewById(R.id.dropdown_roomSize);
        spinnerDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
                spinnerDropdown.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //date picker code and show the output in edittext
        shiftDate = findViewById(R.id.et_shiftDate);
        shiftDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                DatePickerDialog datePickerDialog = new DatePickerDialog(ShiftMyHome.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                shiftDate.setText(day + "/" + month + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });


        Button btnSubmit_smh = (Button) findViewById(R.id.btnSubmit_smh);
        btnSubmit_smh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etname.getText().toString().isEmpty()) {
                            Toast.makeText(ShiftMyHome.this, "All Fields are Mandatory", Toast.LENGTH_SHORT).show();
                        }
                postDataUsingVolley(etname.getText().toString());
            }
        });



    }

    private void postDataUsingVolley(String name) {
        String url = "https://script.google.com/macros/s/AKfycbyjtUKfYcnmlXmKdKyR1-ynqQHt9CJdP4yw8kTiA5XLnF6-dXjASQCdatUArTConsCxPA/exec";
        pg_smh.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(ShiftMyHome.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        etname.setText("");
                        Toast.makeText(ShiftMyHome.this, "Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ShiftMyHome.this, MainActivity.class);
                        startActivity(intent);
                        try {
                            JSONObject respObj = new JSONObject(response);

                            String name = respObj.getString("name");
//                            String mobile = respObj.getString("mobile");
//                            String date = respObj.getString("date");
//                            String roomSize = respObj.getString("roomSize");
//                            String pickupAdd = respObj.getString("pickupAdd");
//                            String dropAdd = respObj.getString("dropAdd");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShiftMyHome.this, "Failed to get response!", Toast.LENGTH_SHORT).show();
            }
    }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

//                params.put("action","addItem");
                params.put("itemName",name);
//                params.put("itemNumber",number);
//                params.put("itemShiftDate",shiftDate);
//                params.put("itemRoomSize",dropdownroomsize);
//                params.put("itemPickupAdd",pickupAdd);
//                params.put("itemDropAdd",dropAdd);

                return params;
            }
        };

        queue.add(stringRequest);
    }
}