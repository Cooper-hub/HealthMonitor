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

public class HealthCarePatientUnregister extends AppCompatActivity {

    private FirebaseFirestore db;
    private Spinner patientSpinner;
    private Button unregisterButton, returnHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_care_patient_unregister);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Now initialize the views after calling setContentView
        unregisterButton = findViewById(R.id.unregisterButton);
        returnHomeButton = findViewById(R.id.returnHomeButton);
        patientSpinner = findViewById(R.id.patientSpinner);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize the Spinner
        patientSpinner = findViewById(R.id.patientSpinner);

        // Fetch patient data from Firestore
        fetchRegisteredPatients();


        // Returning back to the home of the Healthcare worker to perform more action
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(HealthCarePatientUnregister.this, HealthCareHomePage.class);
            startActivity(intent);
        });

        unregisterButton.setOnClickListener(view -> {
            unRegisterPatient();
            fetchRegisteredPatients();
        });
    }

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
