package com.example.healthmonitor;
import android.content.Intent;

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
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    protected EditText emailEt;
    protected EditText passwordEt;
    protected String email;
    protected String password;
    private Button signupBtn;
    private Button loginBtn;

    private TextView resetPasswordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt = findViewById(R.id.Username);
        passwordEt = findViewById(R.id.Password);

        signupBtn = findViewById(R.id.Registerbtn);
        loginBtn = findViewById(R.id.Loginbtn);

        auth = FirebaseAuth.getInstance();

        resetPasswordTv = findViewById(R.id.forgotPassword);

        resetPasswordTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Contact your system administrator for assistance", Toast.LENGTH_SHORT).show();
            }
        });

        loginBtn.setOnClickListener(view -> {
            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        String username = emailEt.getText().toString();

                        String[] parts = username.split("@");

                        //If it is an employee
                        if (parts.length == 2 && parts[1].equals("mytru.ca")) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In Employee", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        //if it is a manager
                        if (parts.length == 2 && parts[1].equals("tru.ca")) {
                            Toast.makeText(LoginActivity.this, "Successfully Logged In Manager", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}