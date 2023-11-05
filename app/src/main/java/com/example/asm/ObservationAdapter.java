package com.example.asm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder> {
    private List<ObservationModel> observations;
    private DatabaseHelper databaseHelper;
    Context context;
    public Button buttonEdit;
    Button buttonDelete;
    public ObservationAdapter(Context context ,List<ObservationModel> observations, DatabaseHelper databaseHelper) {
        this.context = context;
        this.observations = observations;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_observation, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder,final int position) {
        ObservationModel observation = observations.get(position);
        holder.textViewObservation.setText(observation.getObservation());
        holder.textViewTime.setText(observation.getTime());
        holder.textViewComments.setText(observation.getComments());
        buttonDelete = holder.itemView.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObservationModel observationToRemove = observations.get(position);
                boolean isDeleted = databaseHelper.deleteObservation(observationToRemove.getId());
                if (isDeleted) {
                    removeObservation(observationToRemove);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Error deleting observation", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonEdit = holder.itemView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObservationModel selectedObservation = observations.get(position);
                Intent intent = new Intent(context, EditObservationActivity.class);
                intent.putExtra("observation", selectedObservation);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return observations.size();
    }
    public void removeObservation(ObservationModel observationModel) {
        observations.remove(observationModel);
    }
    public class ObservationViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewObservation;
        public TextView textViewTime;
        public TextView textViewComments;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewObservation = itemView.findViewById(R.id.textViewObservation);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComments = itemView.findViewById(R.id.textViewComments);
        }
    }
}