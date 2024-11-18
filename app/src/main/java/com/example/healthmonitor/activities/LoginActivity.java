package com.example.healthmonitor.activities;

import android.content.Intent;
import com.example.healthmonitor.R;
import com.example.healthmonitor.User;
import com.google.firebase.FirebaseApp;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * LoginActivity handles user login for both medical professionals and patients.
 * It allows users to log in using Firebase Authentication and directs them to
 * the appropriate home page based on their email domain.
 */
public class LoginActivity extends AppCompatActivity {

    /** Firebase Authentication instance */
    public FirebaseAuth auth;

    /** Currently logged-in user */
    public static User loggedInUser = new User();

    /** EditText for entering email */
    protected EditText emailEt;

    /** EditText for entering password */
    protected EditText passwordEt;

    /** User's email */
    protected String email;

    /** User's password */
    protected String password;

    /**
     * Called when the activity is first created. Initializes the UI elements,
     * sets up listeners for the login and registration buttons, and handles login functionality.
     *
     * @param savedInstanceState a Bundle containing the activity's previous state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.Username);
        passwordEt = findViewById(R.id.Password);

        Button signupBtn = findViewById(R.id.Registerbtn);
        Button loginBtn = findViewById(R.id.Loginbtn);

        auth = FirebaseAuth.getInstance();

        TextView resetPasswordTv = findViewById(R.id.forgotPassword);

        resetPasswordTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Contact your system administrator for assistance", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener(view -> {

            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();

            loggedInUser.setContactInformation(email);
            loggedInUser.setPassword(password);

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        String username = emailEt.getText().toString();

                        String[] parts = username.split("@");
                        Intent intent;

                        if (parts.length == 2) {
                            String domain = parts[1];

                            // If the email domain is hospital.ca, it's a medical professional
                            if (domain.equals("hospital.ca")) {
                                Toast.makeText(LoginActivity.this, "Successfully Logged In Medical Professional", Toast.LENGTH_LONG).show();
                                intent = new Intent(LoginActivity.this, HealthCareHomePage.class);
                            }
                            // If the email domain is anything else, it's a patient
                            else {
                                Toast.makeText(LoginActivity.this, "Successfully Logged In Patient", Toast.LENGTH_LONG).show();
                                intent = new Intent(LoginActivity.this, PatientHomePage.class);
                            }
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
