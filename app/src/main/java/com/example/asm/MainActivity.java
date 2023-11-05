package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText editTextName, editTextLocation, editTextDate, editTextLength;
    private Spinner spinnerParking, spinnerDifficulty;
    private Button buttonAddHike, buttonViewAll;
    private DatabaseHelper databaseHelper;
    private TextInputLayout tilName, tilLocation, tilDate, tilLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetSpinner();
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLength = findViewById(R.id.editTextLength);
        spinnerParking = findViewById(R.id.spinnerParking);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        buttonAddHike = findViewById(R.id.buttonAddHike);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        tilName = findViewById(R.id.tilName);
        tilLocation = findViewById(R.id.tilLocation);
        tilDate = findViewById(R.id.tilDate);
        tilLength = findViewById(R.id.tilLength);

        databaseHelper = new DatabaseHelper(this);

        buttonAddHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String location = editTextLocation.getText().toString();
                String date = editTextDate.getText().toString();
                String parking = spinnerParking.getSelectedItem().toString();
                String length = editTextLength.getText().toString();
                String difficulty = spinnerDifficulty.getSelectedItem().toString();
                if (validateInput(name, location, date, parking, length, difficulty)) {
                    long result = databaseHelper.insertHike(name, location, date, parking, length, difficulty);
                    if (result != -1) {
                        Toast.makeText(MainActivity.this, "Hike added successfully.", Toast.LENGTH_SHORT).show();
                        clearInputFields();
                        Intent intent = new Intent(MainActivity.this, HikeListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to add hike.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HikeListActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInput(String name, String location, String date, String parking, String length, String difficulty) {
        boolean isValid = true;
        if (name.isEmpty()) {
            tilName.setError("Name is required");
            isValid = false;
        } else {
            tilName.setError(null);
        }

        if (location.isEmpty()) {
            tilLocation.setError("Location is required");
            isValid = false;
        } else {
            tilLocation.setError(null);
        }

        if (date.isEmpty()) {
            tilDate.setError("Date is required");
            isValid = false;
        } else {
            tilDate.setError(null);
        }

        if (length.isEmpty()) {
            tilLength.setError("Length is required");
            isValid = false;
        } else {
            tilLength.setError(null);
        }
        if (parking.isEmpty()) {
            // Handle difficulty validation if needed
            isValid = false;
        }
        if (difficulty.isEmpty()) {
            // Handle difficulty validation if needed
            isValid = false;
        }

        return isValid;
    }

    private void clearInputFields() {
        editTextName.setText("");
        editTextLocation.setText("");
        editTextDate.setText("");
        editTextLength.setText("");
        spinnerParking.setSelection(0);
        spinnerDifficulty.setSelection(0);

        // Clear the error messages
        tilName.setError(null);
        tilLocation.setError(null);
        tilDate.setError(null);
        tilLength.setError(null);
    }
    private void SetSpinner() {
        spinnerParking = findViewById(R.id.spinnerParking);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);

        // Create an adapter as shown below
        ArrayAdapter<CharSequence> parkingAdapter = ArrayAdapter.createFromResource(this, R.array.parking_options, android.R.layout.simple_spinner_item);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulty_options, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        spinnerParking.setAdapter(parkingAdapter);
        spinnerDifficulty.setAdapter(difficultyAdapter);
    }
}