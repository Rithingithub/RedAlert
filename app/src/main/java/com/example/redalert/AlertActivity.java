package com.example.redalert;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class AlertActivity extends AppCompatActivity {

    private boolean exitApp = false;

    // Location variables
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        Button btnCodeRed = findViewById(R.id.btnCodeRed);
        Button btnAlert = findViewById(R.id.btnAlert);
        Button btnHelp = findViewById(R.id.btnHelp);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnGetData = findViewById(R.id.btnGetData);

        // Initialize LocationManager and LocationListener
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // This method will be called when the user's location changes.
                // You can get the latitude and longitude from the 'location' object.
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                // Do something with the latitude and longitude (e.g., display it in a Toast)
                Toast.makeText(AlertActivity.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        btnCodeRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle the "Code Red" button click event here
            }
        });

        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle the "Alert" button click event here
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add code to handle the "Help!" button click event here
            }
        });

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for location permissions
                if (ActivityCompat.checkSelfPermission(AlertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Request location updates
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    // Request location permissions if not granted
                    ActivityCompat.requestPermissions(AlertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exitApp) {
            finishAffinity(); // Finish all activities in the task stack
        } else {
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
            exitApp = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exitApp = false;
                }
            }, 2000); // Reset the flag after 2 seconds (adjust as needed)
        }
    }
}
