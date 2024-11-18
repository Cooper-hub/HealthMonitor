package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.HealthCareRegisterPatient.getDecoratorClasses;
import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.regex.*;

public class HealthCarePatientInformation extends AppCompatActivity {
    private FirebaseFirestore db;
    private LineChart monitorChart;
    private Button displayMonitorInfoButton, displayPatientInfoButton, homeButton;
    private Spinner monitorSpinner, patientSpinner;
    private TextView patientInfoTextView;
    private String fullData;
    private String patientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_patient_information);

        // Initialize views
        monitorChart = findViewById(R.id.monitorChart);
        displayMonitorInfoButton = findViewById(R.id.displayMonitorInfoButton);
        displayPatientInfoButton = findViewById(R.id.displayPatientInfoButton);
        homeButton = findViewById(R.id.homeButton);
        monitorSpinner = findViewById(R.id.monitorSpinner);
        patientSpinner = findViewById(R.id.patientSpinner);
        patientInfoTextView = findViewById(R.id.patientInfoTextView);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        fetchPatientData();


        // Button click listener to show monitor info
        displayMonitorInfoButton.setOnClickListener(v -> {
            // Call the method to set up and display the chart
            setupChart(monitorChart, monitorSpinner.getSelectedItem().toString());
            getLatestMonitorValue(monitorSpinner.getSelectedItem().toString(),patientSpinner.getSelectedItem().toString());
        });

        // Button click listener to display the selected patient's information
        displayPatientInfoButton.setOnClickListener(v -> {
            // Fetch and display the full data of the selected patient
            displayPatientInfo(patientSpinner.getSelectedItem().toString(), patientInfoTextView);
        });

        homeButton.setOnClickListener(v -> {
            Toast.makeText(HealthCarePatientInformation.this, "Returning home", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HealthCarePatientInformation.this, HealthCareHomePage.class);
            startActivity(intent);
        });
    }

    protected void setupChart(LineChart monitorChart, String selectedMonitor) {
        // Access Firestore to get the specific monitor data for the selected patient and monitor type
        db.collection("monitors")
                .document(selectedMonitor)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the unique field name (assuming it is unknown in advance)
                        Map<String, Object> monitorFields = documentSnapshot.getData();
                        if (monitorFields != null && !monitorFields.isEmpty()) {
                            // Retrieve the first field, which is assumed to be the data array
                            String uniqueFieldName = monitorFields.keySet().iterator().next();
                            List<String> monitorData = (List<String>) documentSnapshot.get(uniqueFieldName);

                            if (monitorData != null) {
                                List<Entry> entries = new ArrayList<>();
                                for (int i = 0; i < monitorData.size(); i++) {
                                    try {
                                        float value = Float.parseFloat(monitorData.get(i)); // Convert each data point to float
                                        entries.add(new Entry(i, value)); // Add each entry for the chart
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace(); // Handle invalid number format if needed
                                    }
                                }

                                // Create LineDataSet and populate the chart
                                LineDataSet dataSet = new LineDataSet(entries, selectedMonitor + " Data");
                                dataSet.setColor(Color.BLUE);
                                dataSet.setValueTextColor(Color.BLACK);
                                dataSet.setLineWidth(2f);
                                dataSet.setDrawValues(false);

                                LineData lineData = new LineData(dataSet);
                                monitorChart.setData(lineData);
                                monitorChart.invalidate(); // Refresh the chart
                            } else {
                                Toast.makeText(this, "No data found for selected monitor", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Monitor fields not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Monitor data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to retrieve monitor data", Toast.LENGTH_SHORT).show();
                });
    }
    protected void fetchPatientData() {
        db.collection("users")  // Adjust this to your correct collection path
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> patientNames = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();

                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                String hasHealthcareWorker = document.getString("healthcareWorker");
                                if (Objects.equals(hasHealthcareWorker, loggedInUser.getContactInformation())) {
                                    patientNames.add(document.getId());
                                }
                            }

                            // Populate the Spinner with patient names
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, patientNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            patientSpinner.setAdapter(adapter);
                        }
                    } else {
                        // Handle error
                        task.getException().printStackTrace();
                    }
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

    public static List<Class<?>> findDecoratorsInString(String input) {
        List<Class<?>> matchingDecorators = new ArrayList<>();

        if (input == null || input.trim().isEmpty()) {
            return matchingDecorators;
        }

        // Normalize the input string: convert to lowercase, remove extra spaces
        String normalizedInput = input.replaceAll("\\s+", " ").toLowerCase();
        Log.d("findDecoratorsInString", "Normalized Input: " + normalizedInput);

        // Retrieve decorator classes
        List<Class<?>> decorators = getDecoratorClasses();

        // Check for each simplified decorator name in the normalized input
        for (Class<?> decorator : decorators) {
            // Remove "Decorator" suffix and convert to lowercase
            String decoratorName = decorator.getSimpleName().replace("Decorator", "").toLowerCase();
            Log.d("findDecoratorsInString", "Checking for decorator: " + decoratorName);

            // Create regex pattern to match the decorator name as a whole word
            Pattern pattern = Pattern.compile("\\b" + decoratorName + "\\b");
            Matcher matcher = pattern.matcher(normalizedInput);

            // If a match is found, add the decorator
            if (matcher.find()) {
                Log.d("findDecoratorsInString", "Match found: " + decoratorName);
                matchingDecorators.add(decorator);
            }
        }

        Log.d("findDecoratorsInString", "Matching Decorators: " + matchingDecorators);
        return matchingDecorators;
    }

    protected void fillMonitorSpinner(List<Class<?>> matchingDecorators, Spinner monitorSpinner) {
        List<String> decoratorNames = new ArrayList<>();

        // Extract names of matching decorators and add to the list
        for (Class<?> decorator : matchingDecorators) {
            decoratorNames.add(decorator.getSimpleName()); // Add the decorator name (simple class name)
        }

        // Create an ArrayAdapter to populate the spinner with the names
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, decoratorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        monitorSpinner.setAdapter(adapter);
    }

    protected static String formatPatientInfo(String rawString) {
        // Replace single-line formatting issues with proper line breaks
        return rawString
                .replace("Patient Info: ", "Patient Info:\n")
                .replace("DateOfBirth: ", "  Date of Birth: ")
                .replace("Gender: ", "\n  Gender: ")
                .replace("Height: ", "\n  Height: ")
                .replace("Weight: ", "\n  Weight: ")
                .replace("Chronic Conditions: ", "\n  Chronic Conditions: ")
                .replace("Allergies: ", "\n  Allergies: ")
                .replace("Medications: ", "\n  Medications: ")
                .replace("Surgeries: ", "\n  Surgeries: ")
                .replace("Family History: ", "\n  Family History: ")
                .replace("Vaccination Records: ", "\n  Vaccination Records: ")
                .replace("BloodPressure: ", "\n  Blood Pressure: ")
                .replace("HeartRate: ", "\n  Heart Rate: ")
                .replace("BloodSugar: ", "\n  Blood Sugar: ")
                .replace("OxygenSaturation: ", "\n  Oxygen Saturation: ");
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

    protected void getCurrentMonitorValue(MonitorDataCallback callback, String selectedMonitor) {
        // Access Firestore to get the specific monitor data for the selected patient and monitor type
        db.collection("monitors")
                .document(selectedMonitor)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the unique field name (assuming it is unknown in advance)
                        Map<String, Object> monitorFields = documentSnapshot.getData();
                        if (monitorFields != null && !monitorFields.isEmpty()) {
                            // Retrieve the first field, which is assumed to be the data array
                            String uniqueFieldName = monitorFields.keySet().iterator().next();
                            List<String> monitorData = (List<String>) documentSnapshot.get(uniqueFieldName);
                            callback.onCallback(monitorData.get(0));
                        } else {
                            Toast.makeText(this, "Monitor fields not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Monitor data not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to retrieve monitor data", Toast.LENGTH_SHORT).show();
                });
    }


}

