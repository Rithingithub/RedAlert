package com.example.redalert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText secretCodeEditText;
    private Button submitButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secretCodeEditText = findViewById(R.id.secretCodeEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCode = secretCodeEditText.getText().toString();
                String secretCode = "hello"; // Replace with the secret code you provided to users.

                if (enteredCode.equals(secretCode)) {
                    // Code is correct, proceed to the next activity
                    navigateToNextActivity();
                    Toast.makeText(MainActivity.this, "You are a authorised user !", Toast.LENGTH_SHORT).show();
                } else {
                    // Code is incorrect, show an error message
                    Toast.makeText(MainActivity.this, "Incorrect code. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Check if authentication already held once on this device
        if (isAuthenticationHeldOnce()) {
            // Authentication already succeeded before, proceed to the next activity
            navigateToNextActivity();
        }
    }

    private void navigateToNextActivity() {
        // Mark that authentication has been held once in this device
        markAuthenticationHeldOnce();

        // Navigate to the NextActivity
        // You need to replace NextActivity.class with the actual class of the NextActivity you want to open.
        // For example: Intent intent = new Intent(this, NextActivity.class);
        //               startActivity(intent);
        Intent intent = new Intent(this, AlertActivity.class);
        startActivity(intent);

    }

    private void markAuthenticationHeldOnce() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("authentication_held_once", true);
        editor.apply();
    }

    private boolean isAuthenticationHeldOnce() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("authentication_held_once", false);
    }
}
