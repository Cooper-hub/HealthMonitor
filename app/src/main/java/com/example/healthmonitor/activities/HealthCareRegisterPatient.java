package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.decorators.PatientDecorator;
import com.google.firebase.FirebaseApp;

import com.example.healthmonitor.R;
import com.example.healthmonitor.Patient;
import com.example.healthmonitor.activities.LoginActivity;
import com.example.healthmonitor.decorators.BloodPressureDecorator;
import com.example.healthmonitor.decorators.BloodSugarDecorator;
import com.example.healthmonitor.decorators.BodyTemperatureDecorator;
import com.example.healthmonitor.decorators.HeartRateDecorator;
import com.example.healthmonitor.decorators.OxygenSaturationDecorator;
import com.example.healthmonitor.decorators.PatientInformationDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



public class HealthCareRegisterPatient extends AppCompatActivity {
    private EditText emailEt, passwordEt, confirmPasswordEt, patientInfoEt;
    private Spinner patientSpinner, monitorSpinner;
    private Button setMonitorBtn, registerBtn;
    private IPatient patient = new PatientDecorator(new Patient());

    private String selectedField;
    private Map<String, Method> setterMethodMap;
    private String selectedDecorator;
    public FirebaseAuth auth;

    public FirebaseFirestore db;
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

        monitorSpinner = findViewById(R.id.monitorSpinner);
        setMonitorBtn = findViewById(R.id.setMonitorBtn);
        registerBtn = findViewById(R.id.registerBtn);
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        // Initialize setter method map
        setterMethodMap = new HashMap<>();
        // Populate spinner with field names from PatientInformationDecorator
        populatePatientSpinner();
        populateMonitorSpinner();

        patient = new PatientInformationDecorator(patient);
        registerBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(emailEt.getText().toString()) || TextUtils.isEmpty(passwordEt.getText().toString())) {
                Toast.makeText(HealthCareRegisterPatient.this, "Please fill username and password", Toast.LENGTH_LONG).show();
            } else {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        // Use the authenticated user's unique UID
                        String userId = auth.getCurrentUser().getEmail();
                        // Data to be saved in Firestore
                        Map<String, String> patientData = new HashMap<>();

                        patientData.put("fullData", patient.getValue());
                        patientData.put("healthcareWorker",loggedInUser.getContactInformation());
                        WriteBatch batch = db.batch();
                        DocumentReference docRef1 = db.collection("users").document(userId);
                        batch.set(docRef1, patientData);

                        // Commit the batch to Firestore
                        batch.commit()
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "Batch commit successful");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Firestore", "Batch commit failed", e);
                                    Toast.makeText(HealthCareRegisterPatient.this, "Error saving data", Toast.LENGTH_LONG).show();
                                });

                        Intent intent = new Intent(this, HealthCareHomePage.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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
            Method setter = setterMethodMap.get(selectedField);

            if (setter != null) {

                // Convert the value to the correct type and invoke the setter of basePatient

                setter.invoke(patient, convertValue(inputValue, setter.getParameterTypes()[0]));
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
            // Handle conversion to a List<String> more specifically
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

    private void populateMonitorSpinner() {
        List<String> monitorNames = new ArrayList<>();
        List<Class<?>> decoratorClasses = getDecoratorClasses();
        for (Class<?> decoratorClass : decoratorClasses)
        {
            monitorNames.add(decoratorClass.getSimpleName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monitorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monitorSpinner.setAdapter(adapter);
        monitorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedDecorator = monitorNames.get(position);
            }
            @Override public void onNothingSelected(AdapterView<?> parent)
            {
                selectedDecorator = null;
            } });
        setMonitorBtn.setOnClickListener(view -> {
            if (selectedDecorator != null) {
                try {
                    // Get the selected decorator class
                    Class<?> selectedDecoratorClass = decoratorClasses.get(monitorNames.indexOf(selectedDecorator));
                    Constructor<?> constructor = selectedDecoratorClass.getConstructor(IPatient.class);
                    patient = (IPatient) constructor.newInstance(patient);
                    Toast.makeText(HealthCareRegisterPatient.this, "Added monitor: " + selectedDecorator, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(HealthCareRegisterPatient.this, "Error adding monitor", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HealthCareRegisterPatient.this, "No monitor selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private List<Class<?>> getDecoratorClasses() {
        List<Class<?>> decoratorClasses = new ArrayList<>();
        // Add the decorator classes manually to the list
        decoratorClasses.add(BloodPressureDecorator.class);
        decoratorClasses.add(BloodSugarDecorator.class);
        decoratorClasses.add(BodyTemperatureDecorator.class);
        decoratorClasses.add(HeartRateDecorator.class);
        decoratorClasses.add(OxygenSaturationDecorator.class);
        return decoratorClasses;
    }

}


