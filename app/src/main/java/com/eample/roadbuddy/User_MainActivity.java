package com.eample.roadbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_MainActivity extends AppCompatActivity {

    private historyAdapter adapter;
    private RecyclerView historyrecyclerView;

    // 13-

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference_ticket = firebaseDatabase.getReference("tickets");

        adapter = new historyAdapter(this);
        historyrecyclerView = findViewById(R.id.history_recyclerview);

        historyrecyclerView.setAdapter(adapter);
        historyrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference_ticket.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ticketModel ticket = dataSnapshot.getValue(ticketModel.class);

                    String Plate_number = dataSnapshot.child("Plate_number").getValue(String.class);
                    String car_latitude = dataSnapshot.child("car_latitude").getValue(String.class);
                    String car_longitude = dataSnapshot.child("car_longitude").getValue(String.class);
                    String car_timestamp = dataSnapshot.child("car_timestamp").getValue(String.class);
                    String sign_latitude = dataSnapshot.child("sign_latitude").getValue(String.class);
                    String sign_longitude = dataSnapshot.child("sign_longitude").getValue(String.class);
                    String sign_timestamp = dataSnapshot.child("sign_timestamp").getValue(String.class);
                    String ticketId = dataSnapshot.child("ticketId").getValue(String.class);
                    ticket.setTicketID(ticketId);
                    ticket.setPlate_number(Plate_number);
                    ticket.setCar_latitude(car_latitude);
                    ticket.setCar_longitude(car_longitude);
                    ticket.setCar_timestamp(car_timestamp);
                    ticket.setSign_latitude(sign_latitude);
                    ticket.setSign_longitude(sign_longitude);
                    ticket.setSign_timestamp(sign_timestamp);
                    if (user.getUid().equals(ticketId.substring(13,41))){
                        adapter.add(ticket);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}