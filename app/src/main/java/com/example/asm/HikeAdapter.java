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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder>{
    private List<HikeModel> hikes;
    private Context context;
    private DatabaseHelper databaseHelper;
    public Button buttonEdit;
    Button buttonDelete;
    public HikeAdapter(Context context, List<HikeModel> hikes, DatabaseHelper databaseHelper) {
        this.context = context;
        this.hikes = hikes;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public HikeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_hike, parent, false);
        return new HikeViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HikeViewHolder holder, final int position) {
        HikeModel hike = hikes.get(position);
        holder.textViewHikeName.setText(hike.getName());
        holder.textViewDate.setText(hike.getDate());
        holder.textViewLength.setText(hike.getLength());
        holder.textViewParking.setText(hike.getParking());
        holder.textViewLocation.setText(hike.getLocation());
        holder.textViewDifficulty.setText(hike.getDifficulty());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HikeModel selectedHike = hikes.get(position);
                Intent intent = new Intent(context, ObservationListActivity.class);
                intent.putExtra("hike_id", selectedHike.getId());
                context.startActivity(intent);
            }
        });
        buttonDelete = holder.itemView.findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HikeModel hikeToRemove = hikes.get(position);
                boolean isDeleted = databaseHelper.deleteHike(hikeToRemove.getId());
                if (isDeleted) {
                    removeHike(hikeToRemove);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Error deleting hike", Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonEdit = holder.itemView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HikeModel selectedHike = hikes.get(position);
                Intent intent = new Intent(context, EditHikeActivity.class);
                intent.putExtra("hike", selectedHike);
                context.startActivity(intent);
            }
        });
    }
    public void removeHike(HikeModel hike) {
        hikes.remove(hike);
    }

    public void setData(List<HikeModel> data) {
        hikes = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return hikes.size();
    }

    public class HikeViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHikeName;
        public TextView textViewDate;
        public TextView textViewLocation;
        public TextView textViewParking;
        public TextView textViewLength;
        public TextView textViewDifficulty;
        public HikeViewHolder(View view) {
            super(view);
            textViewHikeName = view.findViewById(R.id.textViewHikeName);
            textViewDate = view.findViewById(R.id.textViewDate);
            textViewLocation = view.findViewById(R.id.textViewLocation);
            textViewLength = view.findViewById(R.id.textViewLength);
            textViewParking = view.findViewById(R.id.textViewParking);
            textViewDifficulty = view.findViewById(R.id.textViewDifficulty);
        }
    }
}