package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditObservationActivity extends AppCompatActivity {
    private ObservationModel selectedObservation;
    private DatabaseHelper databaseHelper;
    private TextInputLayout tilName, tilTime, tilComment;
    private TextInputEditText editTextName, editTextTime, editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        databaseHelper = new DatabaseHelper(this);
        selectedObservation = (ObservationModel) getIntent().getSerializableExtra("observation");

        editTextName = findViewById(R.id.editTextObservation);
        editTextTime = findViewById(R.id.editTextTime);
        editTextComment = findViewById(R.id.editTextComments);
        tilName = findViewById(R.id.tilName);
        tilTime = findViewById(R.id.tilTime);
        tilComment = findViewById(R.id.tilComments);

        editTextName.setText(selectedObservation.getObservation());
        editTextTime.setText(selectedObservation.getTime());
        editTextComment.setText(selectedObservation.getComments());


        Button buttonSave = findViewById(R.id.buttonEdit);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();

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
    private void saveChanges() {

        String newObservation = editTextName.getText().toString();
        String newTime = editTextTime.getText().toString();
        String newComments = editTextComment.getText().toString();
        if (validateInput(newObservation, newTime, newComments)) {
            long result = databaseHelper.updateObservation(
                    selectedObservation.getId(),
                    newObservation,
                    newTime,
                    newComments
            );

            if (result > 0) {
                Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
                clearInputFields();
                finish();
                Intent intent = new Intent(EditObservationActivity.this, ObservationListActivity.class);
                intent.putExtra("hike_id", selectedObservation.getHikeId());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
