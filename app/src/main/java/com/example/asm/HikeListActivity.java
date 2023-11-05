package com.example.asm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HikeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private HikeAdapter hikeAdapter;
    private DatabaseHelper databaseHelper;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterHikes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterHikes(newText);
                return true;
            }
        });
        recyclerView = findViewById(R.id.recyclerViewHikeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        List<HikeModel> hikes = databaseHelper.getAllHikes();

        hikeAdapter = new HikeAdapter(this, hikes,  databaseHelper);
        recyclerView.setAdapter(hikeAdapter);

        Button fabCreateHike = findViewById(R.id.fabCreateHike);
        fabCreateHike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HikeListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    private void filterHikes(String query) {
        List<HikeModel> filteredHikes = databaseHelper.searchHikes(query);
        hikeAdapter.setData(filteredHikes);
        hikeAdapter.notifyDataSetChanged();
    }

}