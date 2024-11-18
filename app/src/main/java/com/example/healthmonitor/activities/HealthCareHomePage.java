package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.R;

/**
 * The main homepage for the Health Care application.
 *
 * This activity serves as a navigation hub, allowing users to:
 * - Register a new patient.
 * - View registered patient information.
 * - Unregister an existing patient.
 */
public class HealthCareHomePage extends AppCompatActivity {

    /**
     * Called when the activity is starting. This method initializes the layout and sets up
     * click listeners for the buttons to navigate to different activities.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down, this contains the data most recently supplied; otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_home_page);

        // Initialize buttons
        Button regPatientBtn = findViewById(R.id.regPatientBtn);
        Button viewPatientsBtn = findViewById(R.id.viewPatientsBtn);
        Button unRegPatientBtn = findViewById(R.id.unRegPatientBtn);

        // Set up button click listeners

        /**
         * Listener for the "Register Patient" button.
         * Navigates to the {@link BasicPatientInfo} activity.
         */
        regPatientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, BasicPatientInfo.class);
            startActivity(intent);
        });

        /**
         * Listener for the "Unregister Patient" button.
         * Navigates to the {@link HealthCarePatientUnregister} activity.
         */
        unRegPatientBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCarePatientUnregister.class);
            startActivity(intent);
        });

        /**
         * Listener for the "View Patients" button.
         * Navigates to the {@link HealthCarePatientInformation} activity.
         */
        viewPatientsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCareHomePage.this, HealthCarePatientInformation.class);
            startActivity(intent);
        });
    }
}
