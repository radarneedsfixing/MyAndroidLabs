package algonquin.cst2335.kuma0194;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button callButton;
    private ImageView imageView;
    private Button changePictureButton;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String PREFS_KEY = "MyPrefs";
    private static final String TELEPHONE_KEY = "TelephoneKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // TextView
        textView = findViewById(R.id.textView);
        textView.setPadding(0, 32, 0, 0); // Set padding: 32 pixels from the top

        // EditText
        editText = findViewById(R.id.editTextPhone);
        editText.setInputType(android.text.InputType.TYPE_CLASS_PHONE); // Set inputType as Phone

        // Button - Call number
        callButton = findViewById(R.id.callButton);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editText.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    Uri number = Uri.parse("tel:" + phoneNumber);
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(dialIntent);

                    // Save the telephone number
                    SharedPreferences prefs = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(TELEPHONE_KEY, phoneNumber);
                    editor.apply();
                }
            }
        });

        // ImageView
        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(android.R.drawable.ic_menu_camera); // Set image resource

        // Button - Change picture
        changePictureButton = findViewById(R.id.buttonChangePicture);
        changePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        // Load the telephone number from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString(TELEPHONE_KEY, "");
        editText.setText(savedPhoneNumber);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);

                // Save the image to disk
                saveImageToDisk(imageBitmap);
            }
        }
    }

    private void saveImageToDisk(Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput("profile_picture.jpg", MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
