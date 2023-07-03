package algonquin.cst2335.kuma0194;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREFS_KEY = "MyPrefs";
    private static final String EMAIL_KEY = "EmailAddress";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the login button
        Button loginButton = findViewById(R.id.loginButton);

        // Set click listener for the login button
        loginButton.setOnClickListener(view -> {
            // Get the email address entered by the user
            EditText emailEditText = findViewById(R.id.editTextEmail);
            String emailAddress = emailEditText.getText().toString();

            // Save the email address to SharedPreferences
            SharedPreferences prefs = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(EMAIL_KEY, emailAddress);
            editor.apply();

            // Create an intent to start the SecondActivity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - Activity is becoming visible to the user");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - Activity is in the foreground and interactive");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Activity is partially visible, but not in focus");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - Activity is no longer visible to the user");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestroy() - Activity is being destroyed and removed from memory");
    }
}
