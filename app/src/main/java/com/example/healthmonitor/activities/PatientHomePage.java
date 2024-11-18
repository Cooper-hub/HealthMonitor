package com.example.healthmonitor.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;

import com.example.healthmonitor.R;

/**
 * PatientHomePage represents the home page for the patient. It provides the interface
 * where the patient can request to view their data by navigating to the PatientDataDisplay activity.
 */
public class PatientHomePage extends AppCompatActivity {

    /**
     * Called when the activity is first created. Initializes the user interface and
     * sets up an event listener on the "Request Data" button. When the button is clicked,
     * it navigates the user to the PatientDataDisplay activity.
     *
     * @param savedInstanceState a Bundle containing the activity's previous state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_page);

        // Initialize the "Request Data" button
        Button request_data_btn = findViewById(R.id.request_btn);

        // Set up a click listener to navigate to the PatientDataDisplay activity when clicked
        request_data_btn.setOnClickListener(view -> {
            Intent intent = new Intent(PatientHomePage.this, PatientDataDisplay.class);
            startActivity(intent);
        });
    }
}
