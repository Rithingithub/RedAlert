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

import com.example.redalert.api.LocationApiService;
import com.example.redalert.api.LocationData;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private boolean exitApp = false;

    // Location variables
    private LocationManager locationManager;
    private LocationListener locationListener;

    // Define your backend server URL here
    private static final String BASE_URL = "http://your_backend_server_url/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Toast.makeText(MainActivity.this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();

                // Send location data to the backend server
                sendLocationDataToServer(latitude, longitude);
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
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Request location updates
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } else {
                    // Request location permissions if not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });
    }

    private void sendLocationDataToServer(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationApiService locationApiService = retrofit.create(LocationApiService.class);
        LocationData locationData = new LocationData(latitude, longitude);

        retrofit2.Call<Void> call = locationApiService.sendLocationData(locationData); // Use the fully qualified name here
        call.enqueue(new retrofit2.Callback<Void>() { // Use the fully qualified name here as well
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    // Location data sent successfully
                    Toast.makeText(MainActivity.this, "Location data sent to server", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error
                    Toast.makeText(MainActivity.this, "Failed to send location data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                // Handle failure
                Toast.makeText(MainActivity.this, "Failed to send location data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
