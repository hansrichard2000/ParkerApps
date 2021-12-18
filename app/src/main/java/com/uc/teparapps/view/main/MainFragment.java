package com.uc.teparapps.view.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uc.teparapps.R;
import com.uc.teparapps.helper.ItemClickSupport;
import com.uc.teparapps.model.LDR;
import com.uc.teparapps.model.ParkingSpace;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    DatabaseReference dbParking, dbLDR;
    ArrayList<ParkingSpace> listParking = new ArrayList<>();
    TextView status_lamp;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbParking = FirebaseDatabase.getInstance().getReference("Parking");
        dbLDR = FirebaseDatabase.getInstance().getReference("LDR").child("status");
        recyclerView = view.findViewById(R.id.rv_parking);
        status_lamp = view.findViewById(R.id.status_lamp);
        fetchParkingData();
        fetchLampData();

    }

    private void fetchLampData() {
        dbLDR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lamp = snapshot.getValue(String.class);
                assert lamp != null;
                if (lamp.equalsIgnoreCase("off")){
                    status_lamp.setTextColor(getResources().getColor(R.color.off));
                    status_lamp.setText("OFF");
                }else if (lamp.equalsIgnoreCase("on")){
                    status_lamp.setTextColor(getResources().getColor(R.color.on));
                    status_lamp.setText("ON");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchParkingData() {
        dbParking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listParking.clear();
                recyclerView.setAdapter(null);
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    ParkingSpace parkingSpace = childSnapshot.getValue(ParkingSpace.class);
                    listParking.add(parkingSpace);
                }
                showparkingData(listParking);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showparkingData(ArrayList<ParkingSpace> listParking) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        MainAdapter mainAdapter = new MainAdapter(requireActivity());
        mainAdapter.setListParking(listParking);
        recyclerView.setAdapter(mainAdapter);

//        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
//            Intent intent = new Intent(LecturerData.this, LecturerDetail.class);
//            Lecturer lecturer = new Lecturer(list.get(position).getId(), list.get(position).getName(), list.get(position).getGender(), list.get(position).getExpertise());
//            intent.putExtra("data_lecturer", lecturer);
//            intent.putExtra("position", position);
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LecturerData.this);
//            startActivity(intent, options.toBundle());
//            finish();
//        });
    }
}