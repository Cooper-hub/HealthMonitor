package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.HealthCareRegisterPatient.getDecoratorClasses;
import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthmonitor.MonitorDataCallback;
import com.example.healthmonitor.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientDataDisplay extends HealthCarePatientInformation {
    private FirebaseFirestore db;
    private LineChart monitorChart;
    private Button displayMonitorInfoButton, displayPatientInfoButton, homeButton;
    private Spinner monitorSpinner;
    private TextView patientInfoTextView;
    private String fullData;
    private String patientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data_display); // Only call this once

        // Initialize views
        monitorChart = findViewById(R.id.monitorChart);
        displayMonitorInfoButton = findViewById(R.id.displayMonitorInfoButton);
        displayPatientInfoButton = findViewById(R.id.displayPatientInfoButton);
        monitorSpinner = findViewById(R.id.monitorSpinner);
        patientInfoTextView = findViewById(R.id.patientInfoTextView);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Button click listener to show monitor info
        displayMonitorInfoButton.setOnClickListener(v -> {
            // Call the method to set up and display the chart
            setupChart(monitorChart, monitorSpinner.getSelectedItem().toString());
            getLatestMonitorValue(monitorSpinner.getSelectedItem().toString(),loggedInUser.getContactInformation());
        });

        // Button click listener to display the selected patient's information
        displayPatientInfoButton.setOnClickListener(v -> {
            // Fetch and display the full data of the selected patient
            displayPatientInfo(loggedInUser.getContactInformation(), patientInfoTextView);
        });
    }

    protected void displayPatientInfo(String selectedPatientId, TextView patientInfoTextView) {
        // Fetch the full data of the selected patient from Firestore
        db.collection("users")
                .document(selectedPatientId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        fullData = documentSnapshot.getString("fullData");
                        patientData = fullData;
                        if (fullData != null) {
                            patientInfoTextView.setText(formatPatientInfo(fullData));
                            List<Class<?>> matchingDecorators = findDecoratorsInString(patientData);
                            Log.d("Spinner Data", "Decorator names: " + matchingDecorators.toString());

                            fillMonitorSpinner(matchingDecorators,monitorSpinner);
                        } else {

                            Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle case where the document does not exist
                        Toast.makeText(this, "Patient not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                });
    }
    protected void getLatestMonitorValue(String selectedMonitor, String selectedPatientId) {
        String processedMonitor = selectedMonitor.endsWith("Decorator")
                ? selectedMonitor.substring(0, selectedMonitor.length() - "Decorator".length())
                : selectedMonitor;

        getCurrentMonitorValue(value -> {
            // This code runs once the monitor data is retrieved
            Log.d("MonitorValue",processedMonitor+  ": " + value);

            // Replace the monitor value in fullData
            String updatedFullData = fullData.replaceAll(
                    "(?i)" + processedMonitor + ":\\s*\\S*",
                    processedMonitor + ": " + value);

            Log.d("New FullData", updatedFullData);

            // Update Firestore with the new fullData
            db.collection("users")
                    .document(selectedPatientId)
                    .update("fullData", updatedFullData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Monitor data updated successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to update monitor data", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    });
        }, monitorSpinner.getSelectedItem().toString());
    }
}