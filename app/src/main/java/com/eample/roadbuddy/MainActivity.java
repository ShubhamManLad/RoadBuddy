package com.eample.roadbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Button npButton;
    Button accidentButton;
    Button historyButton;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(),SelectUser.class);
            startActivity(intent);
            finish();
        }

        npButton = findViewById(R.id.npButton);
        npButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CarActivity.class);
                //intent.putExtra("Action",3);
                startActivity(intent);
            }
        });

        accidentButton = findViewById(R.id.accidentButton);
        accidentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendData.class);
                intent.putExtra("Action",5);
                startActivity(intent);
            }
        });

        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),User_MainActivity.class);
                intent.putExtra("Mode",69);
                startActivity(intent);

            }
        });

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),SelectUser.class);
                startActivity(intent);
                finish();
            }
        });
    }
}