package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditObservationActivity extends AppCompatActivity {
    private ObservationModel selectedObservation;
    private DatabaseHelper databaseHelper;

    private EditText editTextObservation;
    private EditText editTextTime;
    private EditText editTextComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        databaseHelper = new DatabaseHelper(this);
        selectedObservation = (ObservationModel) getIntent().getSerializableExtra("observation");

        editTextObservation = findViewById(R.id.editTextObservation);
        editTextTime = findViewById(R.id.editTextTime);
        editTextComments = findViewById(R.id.editTextComments);

        editTextObservation.setText(selectedObservation.getObservation());
        editTextTime.setText(selectedObservation.getTime());
        editTextComments.setText(selectedObservation.getComments());


        Button buttonSave = findViewById(R.id.buttonEdit);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
                Intent intent = new Intent(EditObservationActivity.this, HikeListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveChanges() {

        String newObservation = editTextObservation.getText().toString();
        String newTime = editTextTime.getText().toString();
        String newComments = editTextComments.getText().toString();

        long result = databaseHelper.updateObservation(
                selectedObservation.getId(),
                newObservation,
                newTime,
                newComments
        );

        if (result > 0) {

            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Log.e("EditObservationActivity", "Error saving changes");
            Toast.makeText(this, "Error saving changes", Toast.LENGTH_SHORT).show();
        }
    }
}
