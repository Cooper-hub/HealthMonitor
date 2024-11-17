package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.R;

public class HealthCareHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_home_page);
        Button regPatientBtn = findViewById(R.id.regPatientBtn);
        Button viewPatientsBtn = findViewById(R.id.viewPatientsBtn);
        Button unRegPatientBtn = findViewById(R.id.unRegPatientBtn);

        regPatientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, BasicPatientInfo.class);
            startActivity(intent);
        });

        unRegPatientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCarePatientUnregister.class);
            startActivity(intent);
        });

        viewPatientsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCarePatientInformation.class);
            startActivity(intent);
        });
    }
}