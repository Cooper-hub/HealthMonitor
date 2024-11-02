package com.example.healthmonitor;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    protected EditText emailEt;
    protected EditText passwordEt;

    public Button signUpBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        emailEt = findViewById(R.id.inputUsername);
        passwordEt = findViewById(R.id.inputPassword);

        loginBtn = findViewById(R.id.alreadyHaveAccount);
        signUpBtn = findViewById(R.id.Registerbtn);

        signUpBtn.setOnClickListener(view -> {
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Test", "Next activity started: ");
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, LoginActivity.class);//changed to spage
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}