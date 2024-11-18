package com.example.healthmonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmonitor.IPatient;
import com.example.healthmonitor.Patient;
import com.example.healthmonitor.R;
import com.example.healthmonitor.User;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Activity for entering and validating basic patient information.
 *
 * This activity allows users to input details such as date of birth, gender, height,
 * and weight for a patient. The entered information is validated, stored, and submitted
 * to the next activity upon confirmation.
 */
public class BasicPatientInfo extends AppCompatActivity {

    /** Static reference to the current patient object being edited or created. */
    public static Patient ppatient;

    /** Input field for the patient's date of birth. */
    private EditText etDateOfBirth;

    /** Input field for the patient's gender. */
    private EditText etGender;

    /** Input field for the patient's height. */
    private EditText etHeight;

    /** Input field for the patient's weight. */
    private EditText etWeight;

    /** Button to submit the patient's information. */
    private Button btnSubmit;

    /**
     * Initializes the activity, including setting up the user interface and click listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down, this contains the data most recently supplied; otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_patient_info);

        // Initialize views
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etGender = findViewById(R.id.etGender);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize patient object
        ppatient = new Patient();

        // Set up the submit button's click listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from EditText fields
                String dateOfBirthInput = etDateOfBirth.getText().toString();
                String genderInput = etGender.getText().toString();
                String heightInput = etHeight.getText().toString();
                String weightInput = etWeight.getText().toString();

                // Validate and set the values
                try {
                    if (!dateOfBirthInput.isEmpty()) {
                        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthInput);
                        ppatient.setDateOfBirth(dateOfBirth);
                    } else {
                        showToast("Please enter a valid Date of Birth");
                        return;
                    }

                    if (!genderInput.isEmpty()) {
                        ppatient.setGender(genderInput);
                    } else {
                        showToast("Please enter a valid Gender");
                        return;
                    }

                    if (!heightInput.isEmpty()) {
                        float height = Float.parseFloat(heightInput);
                        ppatient.setHeight(height);
                    } else {
                        showToast("Please enter a valid Height");
                        return;
                    }

                    if (!weightInput.isEmpty()) {
                        float weight = Float.parseFloat(weightInput);
                        ppatient.setWeight(weight);
                    } else {
                        showToast("Please enter a valid Weight");
                        return;
                    }

                    // Show confirmation and clear fields
                    showToast("Patient info submitted successfully!");
                    clearFields();

                } catch (DateTimeParseException e) {
                    showToast("Invalid date format. Use yyyy-mm-dd.");
                } catch (NumberFormatException e) {
                    showToast("Height and Weight should be numbers.");
                }

                // Navigate to the next activity
                Intent intent = new Intent(BasicPatientInfo.this, HealthCareRegisterPatient.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Displays a Toast message on the screen.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(BasicPatientInfo.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Clears all input fields in the activity.
     */
    private void clearFields() {
        etDateOfBirth.setText("");
        etGender.setText("");
        etHeight.setText("");
        etWeight.setText("");
    }
}
