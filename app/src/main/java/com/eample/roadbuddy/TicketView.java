package com.eample.roadbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TicketView extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_view);

        Intent intent = getIntent();
        String number_plate = intent.getStringExtra("Number_Plate");
        String car_lat = intent.getStringExtra("Latitude");
        String car_long = intent.getStringExtra("Longitude");
        String ticket_id = intent.getStringExtra("Ticket_ID");
        int mode = intent.getIntExtra("Mode",0);

        TextView np = findViewById(R.id.numberplateView);
        TextView clat = findViewById(R.id.latitudeView);
        TextView clong = findViewById(R.id.longitudeView);
        TextView aiv = findViewById(R.id.ai_verifiedView);
        TextView rtov = findViewById(R.id.rto_verifiedView);
        Button verify = findViewById(R.id.rto_verified_Button);

        np.setText(number_plate);
        clat.setText(car_lat);
        clong.setText(car_long);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tickets");

        verify.setVisibility(View.INVISIBLE);

        if (mode!= 69){

            verify.setVisibility(View.VISIBLE);
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String verified = "verified";
                    databaseReference.child(ticket_id).child("rto_verified").setValue(verified);
                }
            });
        }


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(ticket_id)){

                        String ai_verified = dataSnapshot.child("ai_verified").getValue(String.class);
                        String rto_verified = dataSnapshot.child("rto_verified").getValue(String.class);

                        aiv.setText(ai_verified);
                        rtov.setText(rto_verified);

                    }

//                    ticketModel ticket = dataSnapshot.getValue(ticketModel.class);
//                    String Plate_number = dataSnapshot.child("Plate_number").getValue(String.class);
//                    String car_latitude = dataSnapshot.child("car_latitude").getValue(String.class);
//                    String car_longitude = dataSnapshot.child("car_longitude").getValue(String.class);
//                    String car_timestamp = dataSnapshot.child("car_timestamp").getValue(String.class);
//                    String sign_latitude = dataSnapshot.child("sign_latitude").getValue(String.class);
//                    String sign_longitude = dataSnapshot.child("sign_longitude").getValue(String.class);
//                    String sign_timestamp = dataSnapshot.child("sign_timestamp").getValue(String.class);
//                    String ticketId = dataSnapshot.child("ticketId").getValue(String.class);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}