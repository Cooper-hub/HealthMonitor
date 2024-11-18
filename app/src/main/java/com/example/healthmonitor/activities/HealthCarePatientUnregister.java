package com.example.healthmonitor.activities;

import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthmonitor.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Activity for unregistering patients assigned to a healthcare worker.
 * This activity allows the healthcare worker to:
 * - View the list of assigned patients.
 * - Unregister a selected patient.
 * - Navigate back to the healthcare worker's home page.
 */
public class HealthCarePatientUnregister extends AppCompatActivity {

    private FirebaseFirestore db;
    private Spinner patientSpinner;
    private Button unregisterButton, returnHomeButton;

    /**
     * Initializes the activity, sets up the UI, and fetches patient data from Firestore.
     *
     * @param savedInstanceState the saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_care_patient_unregister);

        // Set up window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        unregisterButton = findViewById(R.id.unregisterButton);
        returnHomeButton = findViewById(R.id.returnHomeButton);
        patientSpinner = findViewById(R.id.patientSpinner);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch and display registered patients
        fetchRegisteredPatients();

        // Set up button listeners
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCarePatientUnregister.this, HealthCareHomePage.class);
            startActivity(intent);
        });

        unregisterButton.setOnClickListener(view -> {
            unRegisterPatient();
            fetchRegisteredPatients();
        });
    }

    /**
     * Fetches the list of patients assigned to the currently logged-in healthcare worker
     * from Firestore and populates the spinner with their names.
     */
    private void fetchRegisteredPatients() {
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

    /**
     * Unregisters the selected patient by removing the healthcare worker assignment
     * in Firestore.
     */
    private void unRegisterPatient() {
        // Get the selected patient ID from the spinner
        String selectedPatientId = patientSpinner.getSelectedItem().toString();
        if (selectedPatientId != null) {
            // Update the selected patient's document to set the healthcareWorker field to null
            db.collection("users")
                    .document(selectedPatientId)
                    .update("healthcareWorker", null)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(HealthCarePatientUnregister.this, "Patient unregistered", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        e.printStackTrace();
                    });
        }
    }
}

