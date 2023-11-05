package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ObservationActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private TextInputLayout tilName, tilTime, tilComment;
    private TextInputEditText editTextName, editTextTime, editTextComment;
    private Button buttonAdd;
    private Button buttonViewAll;
    int selectedHikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        selectedHikeId = (int) getIntent().getSerializableExtra("hike_id");

        editTextName = findViewById(R.id.editTextObservation);
        editTextTime = findViewById(R.id.editTextTime);
        editTextComment = findViewById(R.id.editTextComments);
        tilName = findViewById(R.id.tilName);
        tilTime = findViewById(R.id.tilTime);
        tilComment = findViewById(R.id.tilComments);
        databaseHelper = new DatabaseHelper(this);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveObservation();
                Intent intent = new Intent(ObservationActivity.this, ObservationListActivity.class);
                intent.putExtra("hike_id", selectedHikeId);
                startActivity(intent);
            }
        });
        buttonViewAll = findViewById((R.id.buttonViewAll));
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationActivity.this, ObservationListActivity.class);
                intent.putExtra("hike_id", selectedHikeId);
                startActivity(intent);
            }
        });
    }
    private boolean validateInput(String name, String location, String date) {
        boolean isValid = true;
        if (name.isEmpty()) {
            tilName.setError("Name is required");
            isValid = false;
        } else {
            tilName.setError(null);
        }

        if (location.isEmpty()) {
            tilTime.setError("Time is required");
            isValid = false;
        } else {
            tilTime.setError(null);
        }

        if (date.isEmpty()) {
            tilComment.setError("Comments is required");
            isValid = false;
        } else {
            tilComment.setError(null);
        }
        return isValid;
    }
    private void clearInputFields() {
        editTextName.setText("");
        editTextTime.setText("");
        editTextComment.setText("");

        tilName.setError(null);
        tilTime.setError(null);
        tilComment.setError(null);
    }
    private void saveObservation() {
        String observation = editTextName.getText().toString();
        String time = editTextTime.getText().toString();
        String comments = editTextComment.getText().toString();
        if (validateInput(observation, time, comments)) {
            long result = databaseHelper.insertObservation(selectedHikeId, observation, time, comments);

            if (result > 0) {

                Toast.makeText(this, "Observation saved", Toast.LENGTH_SHORT).show();
                clearInputFields();
            } else {
                Toast.makeText(this, "Error saving observation", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
