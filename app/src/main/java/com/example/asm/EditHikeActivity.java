package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class EditHikeActivity extends AppCompatActivity {
    private HikeModel selectedHike;
    private DatabaseHelper databaseHelper;
    private TextInputLayout tilName, tilLocation, tilDate, tilLength;
    private TextInputEditText editTextName, editTextLocation, editTextDate, editTextLength;
    private Spinner spinnerParking;
    private Spinner spinnerDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);

        databaseHelper = new DatabaseHelper(this);

        selectedHike = (HikeModel) getIntent().getSerializableExtra("hike");


        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDate = findViewById(R.id.editTextDate);
        spinnerParking = findViewById(R.id.spinnerParking);
        editTextLength = findViewById(R.id.editTextLength);
        spinnerDifficulty = findViewById(R.id.spinnerDifficulty);
        tilName = findViewById(R.id.tilName);
        tilLocation = findViewById(R.id.tilLocation);
        tilDate = findViewById(R.id.tilDate);
        tilLength = findViewById(R.id.tilLength);
        editTextName.setText(selectedHike.getName());
        editTextLocation.setText(selectedHike.getLocation());
        editTextDate.setText(selectedHike.getDate());
        editTextLength.setText(selectedHike.getLength());

        ArrayAdapter<CharSequence> parkingAdapter = ArrayAdapter.createFromResource(this, R.array.parking_options, android.R.layout.simple_spinner_item);
        parkingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParking.setAdapter(parkingAdapter);
        int parkingPosition = parkingAdapter.getPosition(selectedHike.getParking());
        spinnerParking.setSelection(parkingPosition);

        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this, R.array.difficulty_options, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);
        int difficultyPosition = difficultyAdapter.getPosition(selectedHike.getDifficulty());
        spinnerDifficulty.setSelection(difficultyPosition);


        Button buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();


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
    private void saveChanges() {

        String newName = editTextName.getText().toString();
        String newLocation = editTextLocation.getText().toString();
        String newDate = editTextDate.getText().toString();
        String newParking = spinnerParking.getSelectedItem().toString();
        String newLength = editTextLength.getText().toString();
        String newDifficulty = spinnerDifficulty.getSelectedItem().toString();
        if (validateInput(newName, newLocation, newDate, newParking, newLength, newDifficulty)) {
            long result = databaseHelper.updateHike(selectedHike.getId(),newName, newLocation, newDate, newParking, newLength, newDifficulty);
            if (result > 0) {
                Toast.makeText(this, "Changes saved.", Toast.LENGTH_SHORT).show();
                clearInputFields();
                Intent intent = new Intent(this, HikeListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error saving changes.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
