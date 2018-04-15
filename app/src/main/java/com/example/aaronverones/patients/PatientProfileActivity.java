package com.example.aaronverones.patients;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PatientProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra(PatientsListAdapter.EXTRA_NAME);
        String id = intent.getStringExtra(PatientsListAdapter.EXTRA_ID);


    }
}
