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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    public FirebaseAuth auth;
    public static User loggedInUser = new User();
    protected EditText emailEt;
    protected EditText passwordEt;
    protected String email;
    protected String password;

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