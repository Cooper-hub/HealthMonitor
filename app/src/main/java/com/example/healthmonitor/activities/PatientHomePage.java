package com.example.healthmonitor.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;

import com.example.healthmonitor.R;

public class PatientHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);

        Button help_btn = findViewById(R.id.help_btn);
        Button request_data_btn = findViewById(R.id.request_btn);

        help_btn.setOnClickListener(view -> {
            Intent intent = new Intent(PatientHomePage.this, PatientRequestAid.class);
            startActivity(intent);
        });

        request_data_btn.setOnClickListener(view -> {
            Intent intent = new Intent(PatientHomePage.this, PatientDataDisplay.class);
            startActivity(intent);
        });
    }
}