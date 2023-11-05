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


public class EditHikeActivity extends AppCompatActivity {
    private HikeModel selectedHike;
    private DatabaseHelper databaseHelper;

    private EditText editTextName;
    private EditText editTextLocation;
    private EditText editTextDate;
    private Spinner spinnerParking;
    private EditText editTextLength;
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

                Intent intent = new Intent(EditHikeActivity.this, HikeListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveChanges() {

        String newName = editTextName.getText().toString();
        String newLocation = editTextLocation.getText().toString();
        String newDate = editTextDate.getText().toString();
        String newParking = spinnerParking.getSelectedItem().toString();
        String newLength = editTextLength.getText().toString();
        String newDifficulty = spinnerDifficulty.getSelectedItem().toString();
        long result = databaseHelper.updateHike(
                selectedHike.getId(),
                newName,
                newLocation,
                newDate,
                newParking,
                newLength,
                newDifficulty
        );

        if (result > 0) {
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show();
        }
    }
}
