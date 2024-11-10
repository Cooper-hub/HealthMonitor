package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.HealthCareRegisterPatient.getDecoratorClasses;
import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.*;
public class HealthCarePatientInformation extends AppCompatActivity {
    private FirebaseFirestore db;
    private LineChart monitorChart;
    private Button displayMonitorInfoButton, displayPatientInfoButton;
    private Spinner monitorSpinner, patientSpinner;
    private TextView patientInfoTextView;

    private String patientData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_patient_information);

        // Initialize views
        monitorChart = findViewById(R.id.monitorChart);
        displayMonitorInfoButton = findViewById(R.id.displayMonitorInfoButton);
        displayPatientInfoButton = findViewById(R.id.displayPatientInfoButton);
        monitorSpinner = findViewById(R.id.monitorSpinner);
        patientSpinner = findViewById(R.id.patientSpinner);
        patientInfoTextView = findViewById(R.id.patientInfoTextView);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        fetchPatientData();



        // Button click listener to show monitor info
        displayMonitorInfoButton.setOnClickListener(v -> {
            // Call the method to set up and display the chart
            setupChart();
        });

        // Button click listener to display the selected patient's information
        displayPatientInfoButton.setOnClickListener(v -> {
            // Fetch and display the full data of the selected patient
            displayPatientInfo();
        });
    }

    private void setupChart() {
        // Example array of strings representing the data
        String[] monitorData = {"12", "15", "18", "20", "25", "28", "32"};

        // Convert the data into Entry objects for the chart
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < monitorData.length; i++) {
            try {
                float value = Float.parseFloat(monitorData[i]); // Convert the string to float
                entries.add(new Entry(i, value)); // Add the entry to the chart's data
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Handle invalid number format if needed
            }
        }

        // Create a LineDataSet to hold the data
        LineDataSet dataSet = new LineDataSet(entries, "Monitor Data");
        dataSet.setColor(Color.BLUE); // Set line color
        dataSet.setValueTextColor(Color.BLACK); // Set value text color
        dataSet.setLineWidth(2f); // Set the line width
        dataSet.setDrawValues(false); // Hide values on the chart points (optional)

        // Create LineData object and set it to the chart
        LineData lineData = new LineData(dataSet);
        monitorChart.setData(lineData);
        monitorChart.invalidate(); // Refresh the chart
    }

    private void fetchPatientData() {
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
    private void displayPatientInfo() {
        // Get the selected patient ID from the spinner
        String selectedPatientId = patientSpinner.getSelectedItem().toString();

        // Fetch the full data of the selected patient from Firestore
        db.collection("users")
                .document(selectedPatientId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String fullData = documentSnapshot.getString("fullData");
                        patientData = fullData;
                        if (fullData != null) {
                            patientInfoTextView.setText(fullData);
                            List<Class<?>> matchingDecorators = findDecoratorsInString(patientData);
                            Log.d("Spinner Data", "Decorator names: " + matchingDecorators.toString());

                            fillMonitorSpinner(matchingDecorators);
                        } else {

                            Toast.makeText(HealthCarePatientInformation.this, "No Data found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle case where the document does not exist
                        Toast.makeText(HealthCarePatientInformation.this, "Patient not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                    Toast.makeText(HealthCarePatientInformation.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
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




    private void fillMonitorSpinner(List<Class<?>> matchingDecorators) {
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

}
