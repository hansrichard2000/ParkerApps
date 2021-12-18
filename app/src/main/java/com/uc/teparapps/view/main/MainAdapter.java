package com.uc.teparapps.view.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uc.teparapps.R;
import com.uc.teparapps.model.ParkingSpace;

import java.util.ArrayList;
import java.util.Objects;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CardViewViewHolder> {
    private Context context;
    private ArrayList<ParkingSpace> listParking;
    private static final String TAG = "MainAdapter";

    public MainAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<ParkingSpace> getListParking() {
        return listParking;
    }

    public void setListParking(ArrayList<ParkingSpace> listParking) {
        this.listParking = listParking;
    }

    @NonNull
    @Override
    public MainAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_spot, parent, false);
        return new MainAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CardViewViewHolder holder, int position) {
        final ParkingSpace parkingSpace = getListParking().get(position);
        holder.park_code.setText(parkingSpace.getParkCode());
        Log.d(TAG, "Status: "+parkingSpace.getStatus());
        if (Objects.equals(parkingSpace.getStatus(), "off")){
//            holder.park_spot.setColorFilter(ContextCompat.getColor(context, R.color.parkingEmpty), android.graphics.PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(holder.park_spot.getDrawable(), ContextCompat.getColor(context, R.color.parkingEmpty));
        }else if (parkingSpace.getStatus().equals("on")){
            DrawableCompat.setTint(holder.park_spot.getDrawable(), ContextCompat.getColor(context, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return getListParking().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        TextView park_code;
        ImageView park_spot;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            park_code = itemView.findViewById(R.id.park_code);
            park_spot = itemView.findViewById(R.id.park_spot);
        }
    }
}
