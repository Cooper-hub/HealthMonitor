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
        Button reg_patient_btn = findViewById(R.id.reg_patient_btn);
        Button view_patients_btn = findViewById(R.id.view_patients_btn);

        reg_patient_btn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCareRegisterPatient.class);
            startActivity(intent);
        });

        view_patients_btn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCareViewPatientList.class);
            startActivity(intent);
        });
    }
}