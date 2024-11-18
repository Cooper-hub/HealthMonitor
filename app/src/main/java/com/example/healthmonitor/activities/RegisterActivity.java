package com.example.healthmonitor.activities;
import static com.example.healthmonitor.activities.LoginActivity.loggedInUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * RegisterActivity handles the user registration process for health care workers.
 * It includes fields for entering an email and password and verifies the data before
 * creating a new account. Upon successful registration, it adds the health care worker
 * to the Firestore database and redirects to the LoginActivity.
 */
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    protected EditText emailEt;
    protected EditText passwordEt;
    public FirebaseFirestore db;

    /**
     * Called when the activity is created. Initializes the necessary components, such
     * as the Firebase authentication system, Firestore instance, and UI elements.
     * Sets up click listeners for the registration and login buttons.
     *
     * @param savedInstanceState a Bundle containing the activity's previous state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailEt = findViewById(R.id.inputUsername);
        passwordEt = findViewById(R.id.inputPassword);

        Button loginBtn = findViewById(R.id.alreadyHaveAccount);
        Button signUpBtn = findViewById(R.id.Registerbtn);

        // Set up the sign-up button to create a user account
        signUpBtn.setOnClickListener(view -> {
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            // Check if the email and password fields are empty
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            } else {
                // Create a new user with Firebase authentication
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Test", "Next activity started: ");
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        addHealthCareWorkerToDB();
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Set up the login button to navigate to the login screen
        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Adds the newly registered health care worker to the Firestore database by creating
     * an empty document in the "users" collection with the email as the document ID.
     */
    private void addHealthCareWorkerToDB() {
        String email = emailEt.getText().toString();
        if (!TextUtils.isEmpty(email)) {
            // Add an empty document to the "users" collection
            db.collection("users")
                    .document(email)
                    .set(new HashMap<>())
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(RegisterActivity.this, "Health Care Worker Added", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(RegisterActivity.this, "Error adding health care worker", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Email is required to add the Health Care Worker", Toast.LENGTH_LONG).show();
        }
    }
}
