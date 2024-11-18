package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;
import static com.example.healthmonitor.activities.BasicPatientInfo.ppatient;
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

import com.example.healthmonitor.IPatient;
import com.example.healthmonitor.decorators.PatientDecorator;
import com.google.firebase.FirebaseApp;

import com.example.healthmonitor.R;
import com.example.healthmonitor.Patient;
import com.example.healthmonitor.decorators.BloodPressureDecorator;
import com.example.healthmonitor.decorators.BloodSugarDecorator;
import com.example.healthmonitor.decorators.BodyTemperatureDecorator;
import com.example.healthmonitor.decorators.HeartRateDecorator;
import com.example.healthmonitor.decorators.OxygenSaturationDecorator;
import com.example.healthmonitor.decorators.PatientInformationDecorator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * Handles the registration of patients and their health monitors in the HealthCare app.
 * Provides functionality for:
 * - Registering patients with email and password using Firebase Authentication.
 * - Adding patient details and monitors to Firestore.
 * - Dynamically managing patient attributes and health monitor decorators.
 */
public class HealthCareRegisterPatient extends AppCompatActivity {
    private EditText emailEt, passwordEt, confirmPasswordEt, patientInfoEt;
    private Spinner patientSpinner, monitorSpinner;
    private Button setMonitorBtn, registerBtn;
    private IPatient patient = new PatientDecorator(ppatient);

    private String selectedField;
    private Map<String, Method> setterMethodMap;
    private String selectedDecorator;
    public FirebaseAuth auth;

    public FirebaseFirestore db;
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
                                    addPatientToHealthCareWorker();
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

        /**
         * Populates the patient attribute spinner with fields from PatientInformationDecorator.
         * Configures its behavior to save field values entered by the user.
         */
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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedField = fieldNames.get(position);

                // Only save if the user has typed something into the EditText
                if (!patientInfoEt.getText().toString().trim().isEmpty()) {
                    saveFieldValue(); // Save the value from the EditText
                    patientInfoEt.setText(""); // Clear the EditText after saving
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                patientInfoEt.setText("");
            }
        });

    }

    /**
     * Formats a field name by inserting spaces before uppercase letters.
     *
     * @param fieldName the original field name.
     * @return the formatted field name.
     */
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

    /**
     * Saves the value entered in the EditText field to the corresponding patient attribute.
     */
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

    /**
     * Converts the input value from a string to the specified field type.
     *
     * @param inputValue the input value as a string
     * @param fieldType the target type to which the input value should be converted
     * @return the converted value as an object, or {@code null} if the type is unsupported
     * @throws NumberFormatException if the input value cannot be converted to a numeric type
     */
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

    /**
     * Populates the monitor spinner with available decorator classes and sets up
     * event listeners for item selection and monitor addition.
     */
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

    /**
     * Retrieves a list of available decorator classes for monitors.
     *
     * @return a list of {@link Class} objects representing the decorator classes
     */
    public static List<Class<?>> getDecoratorClasses() {
        List<Class<?>> decoratorClasses = new ArrayList<>();
        // Add the decorator classes manually to the list
        decoratorClasses.add(BloodPressureDecorator.class);
        decoratorClasses.add(BloodSugarDecorator.class);
        decoratorClasses.add(BodyTemperatureDecorator.class);
        decoratorClasses.add(HeartRateDecorator.class);
        decoratorClasses.add(OxygenSaturationDecorator.class);
        return decoratorClasses;
    }
    /**
     * Adds the selected patient to the healthcare worker's list of observed patients
     * in the database.
     */
    private void addPatientToHealthCareWorker() {
        String patientEmail = emailEt.getText().toString();

        // Update the selected patient's document to add the patient to the "patients" array
        db.collection("users")
                .document(loggedInUser.getContactInformation())  // Healthcare worker's document
                .update("patients", FieldValue.arrayUnion(patientEmail))  // Add patientEmail to the patients array
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(HealthCareRegisterPatient.this, "Patient now being observed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                });
    }
}


