package com.example.healthmonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.R;
import com.example.healthmonitor.Patient;
import com.example.healthmonitor.decorators.PatientInformationDecorator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HealthCareRegisterPatient extends AppCompatActivity {

    private EditText emailEt, passwordEt, confirmPasswordEt, patientInfoEt;
    private Spinner patientSpinner;
    private PatientInformationDecorator patientInfoDecorator;
    private String selectedField;
    private Map<String, Method> setterMethodMap;

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

        // Initialize patient decorator
        Patient basePatient = new Patient();
        patientInfoDecorator = new PatientInformationDecorator(basePatient) {
        };

        // Initialize setter method map
        setterMethodMap = new HashMap<>();

        // Populate spinner with field names from PatientInformationDecorator
        populatePatientSpinner();


    }

    private void populatePatientSpinner() {
        List<String> fieldNames = new ArrayList<>();

        // Get all setter methods from PatientInformationDecorator
        Method[] methods = PatientInformationDecorator.class.getDeclaredMethods();
        for (Method method : methods) {
            // Check if the method is a setter (starts with "set")
            if (method.getName().startsWith("set")) {
                // Get the corresponding field name by stripping "set" and converting the first letter to lowercase
                String fieldName = method.getName().substring(3);

                // Add the formatted field name to the list for display purposes
                fieldNames.add(formatFieldName(fieldName));

                // Store the setter method mapped to the original (unformatted) field name
                setterMethodMap.put(formatFieldName(fieldName), method);
            }
        }

        // Create the adapter and set it to the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fieldNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(adapter);

        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedField = fieldNames.get(position);

                // Only save if the user has typed something into the EditText
                if (!patientInfoEt.getText().toString().trim().isEmpty()) {
                    saveFieldValue(); // Save the value from the EditText
                    patientInfoEt.setText(""); // Clear the EditText after saving
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                patientInfoEt.setText("");
            }
        });

    }

    private String formatFieldName(String fieldName) {
        StringBuilder formattedName = new StringBuilder();
        for (char c : fieldName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                formattedName.append(" ");
            }
            formattedName.append(c);
        }
        return formattedName.toString().trim();
    }

    private void saveFieldValue() {
        try {
            // Get the input value from the EditText field
            String inputValue = patientInfoEt.getText().toString().trim();

            // Check if the input value is empty
            if (inputValue.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get the corresponding setter method for the selected field (using unformatted field name)
            Method setter = setterMethodMap.get(selectedField);

            // Debugging: Show the setter method being called
            if (setter != null) {
                Toast.makeText(this, "Calling setter: " + setter.getName(), Toast.LENGTH_SHORT).show();

                // Convert the value to the correct type and invoke the setter
                setter.invoke(patientInfoDecorator, convertValue(inputValue, setter.getParameterTypes()[0]));

                // Display a toast with the field name and the saved value
                Toast.makeText(this, "Saved " + selectedField + ": " + inputValue, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Setter method not found for " + selectedField, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving value for " + selectedField, Toast.LENGTH_SHORT).show();
        }
    }




    private Object convertValue(String inputValue, Class<?> fieldType) {
        if (fieldType == List.class) {
            // Split input by commas to form a List<String>
            return Arrays.asList(inputValue.split("\\s*,\\s*"));
        } else if (fieldType == String.class) {
            return inputValue;
        } else if (fieldType == int.class) {
            return Integer.parseInt(inputValue);
        } else if (fieldType == boolean.class) {
            return Boolean.parseBoolean(inputValue);
        } else if (fieldType == float.class) {
            return Float.parseFloat(inputValue);
        } else if (fieldType == double.class) {
            return Double.parseDouble(inputValue);
        }
        // Add more conversions as needed
        return null;
    }
}
