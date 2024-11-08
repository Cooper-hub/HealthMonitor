package com.example.healthmonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.R;
import com.example.healthmonitor.Patient;
import com.example.healthmonitor.decorators.PatientInformationDecorator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HealthCareRegisterPatient extends AppCompatActivity {

    private EditText emailEt, passwordEt, confirmPasswordEt, patientInfoEt;
    private Spinner patientSpinner;
    private PatientInformationDecorator patientInfoDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_care_register_patient);

        // Initialize UI components
        emailEt = findViewById(R.id.inputUsername);
        passwordEt = findViewById(R.id.inputPassword);
        confirmPasswordEt = findViewById(R.id.inputConfirmPassword);
        patientInfoEt = findViewById(R.id.patientInfoInput);
        patientSpinner = findViewById(R.id.patientSpinner);

        // Initialize patient decorator (for demo purposes, use an anonymous subclass if PatientInformationDecorator is abstract)
        Patient basePatient = new Patient();
        patientInfoDecorator = new PatientInformationDecorator(basePatient) {};

        // Populate spinner with field names from PatientInformationDecorator
        populatePatientSpinner();
    }

    private void populatePatientSpinner() {
        // Retrieve fields dynamically from PatientInformationDecorator
        List<String> fieldNames = new ArrayList<>();
        for (Field field : PatientInformationDecorator.class.getDeclaredFields()) {
            fieldNames.add(formatFieldName(field.getName()));
        }

        // Set up spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fieldNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(adapter);

        // Set spinner selection listener to display values in patientInfoEt
        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedField = fieldNames.get(position);
                displayFieldValue(selectedField);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Clear the EditText if nothing is selected
                patientInfoEt.setText("");
            }
        });
    }

    private String formatFieldName(String fieldName) {
        // Format field name to be more readable (e.g., "chronicConditions" -> "Chronic Conditions")
        StringBuilder formattedName = new StringBuilder();
        for (char c : fieldName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                formattedName.append(" ");
            }
            formattedName.append(c);
        }
        return formattedName.toString().trim();
    }

    private void displayFieldValue(String fieldName) {
        try {
            // Access field value using reflection
            Field field = PatientInformationDecorator.class.getDeclaredField(fieldName);
            field.setAccessible(true); // Allow access to private fields

            // Retrieve and display the value
            Object value = field.get(patientInfoDecorator);
            patientInfoEt.setText(value != null ? value.toString() : "No data available");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error retrieving value for " + fieldName, Toast.LENGTH_SHORT).show();
        }
    }
}
