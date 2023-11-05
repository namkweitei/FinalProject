package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservationListActivity extends AppCompatActivity {
    List<ObservationModel> observationModels;
    private RecyclerView recyclerView;
    private ObservationAdapter observationAdapter;
    private DatabaseHelper databaseHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list);
//        searchView = findViewById(R.id.searchView);
//        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filterHikes(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterHikes(newText);
//                return true;
//            }
//        });
        recyclerView = findViewById(R.id.recyclerViewObservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        int hikeid = (int) getIntent().getSerializableExtra("hike_id");
        observationModels = databaseHelper.getObservationsForHike(hikeid);

        observationAdapter = new ObservationAdapter(this, observationModels,  databaseHelper);
        recyclerView.setAdapter(observationAdapter);

        Button fabCreateHike = findViewById(R.id.fabCreateHike);
        fabCreateHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationListActivity.this, ObservationActivity.class);
                intent.putExtra("hike_id", hikeid);
                startActivity(intent);
            }
        });



    }

//    private void filterHikes(String query) {
//        List<ObservationModel> filteredHikes = databaseHelper.searchHikes(query);
//        observationAdapter.setData(filteredHikes);
//        observationAdapter.notifyDataSetChanged();
//    }
}
