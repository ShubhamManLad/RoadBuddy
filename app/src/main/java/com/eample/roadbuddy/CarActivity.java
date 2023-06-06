package com.eample.roadbuddy;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.Date;

public class CarActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;

    private TextView txt;
    private ImageView imgCar;
    byte[] car;
    private Button carSendButton;

    String photoID;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReferenceCar;

    String latitude;
    String longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        ActivityResultLauncher<String> cameraPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (!result){
                    Toast.makeText(CarActivity.this, "Grant Camera Permission", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            cameraPermission.launch(Manifest.permission.CAMERA);
        }

        imgCar = findViewById(R.id.carImageView);
        imgCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    cameraPermission.launch(Manifest.permission.CAMERA);
                }
                else{
                    Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePic,CAMERA_REQUEST);
                }

            }
        });

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("tickets");
        firebaseStorage = FirebaseStorage.getInstance();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        carSendButton = findViewById(R.id.sendCarButton);
        carSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                //This method returns the time in millis
                long time = date.getTime();
                String timestamp = String.valueOf(time);
                photoID = timestamp + user.getUid().toString();

                if (ActivityCompat.checkSelfPermission
                        (getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
                        && ActivityCompat.checkSelfPermission
                        (getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    getCurrentLocation();

                }
                else{
                    ActivityCompat.requestPermissions(CarActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }

                storageReferenceCar = firebaseStorage.getReference("car/"+photoID+".jpg");

                storageReferenceCar.putBytes(car).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(CarActivity.this, "Car Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignActivity.class);
                        intent.putExtra("ticketId",photoID);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

    }


    // Location permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && (grantResults[0] + grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null){
//                        txt.setText(String.valueOf(location.getLatitude()));
//                        txt2.setText(String.valueOf(location.getLongitude()));


                        databaseReference.child(photoID).child("car_latitude").setValue(String.valueOf(location.getLatitude()));
                        databaseReference.child(photoID).child("car_longitude").setValue(String.valueOf(location.getLongitude()));
                        databaseReference.child(photoID).child("car_timestamp").setValue(photoID.substring(0,13));


                    }
                    else{
                        com.google.android.gms.location.LocationRequest locationRequest = new com.google.android.gms.location.LocationRequest()
                                .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {

                                Location location1 = locationResult.getLastLocation();

                                databaseReference.child(photoID).child("car_latitude").setValue(String.valueOf(location.getLatitude()));
                                databaseReference.child(photoID).child("car_longitude").setValue(String.valueOf(location.getLongitude()));
                                databaseReference.child(photoID).child("car_timestamp").setValue(photoID.substring(0,13));

                            }
                        };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }
                }
            });
        }
        else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    // Camera Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBtm = (Bitmap) extras.get("data");
            imgCar.setImageBitmap(imgBtm);
            imgCar.setDrawingCacheEnabled(true);
            imgCar.buildDrawingCache();
            Bitmap bitmap = imgCar.getDrawingCache();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            car = baos.toByteArray();
        }

    }
}