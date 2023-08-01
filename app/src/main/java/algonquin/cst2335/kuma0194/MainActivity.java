package algonquin.cst2335.kuma0194;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

 import algonquin.cst2335.kuma0194.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected ActivityMainBinding binding;
    RequestQueue queue = null;
    protected Bitmap image;


    @Override
    public void setContentView(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);

        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        super.setContentView(binding.getRoot());
        binding.forecastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=7e943c97096a9784391a981c4d878b22&units=metric"
                ;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    // Lambda expression for successful response
                    response -> {
                        try{
                            // Get the JSONObject associated with "coord"
                            JSONObject coord = response.getJSONObject("coord");

                            // Get the JSONArray associated with "weather"
                            JSONArray weatherArray = response.getJSONArray("weather");

                            // Get the JSONObject at position 0
                            JSONObject position0 = weatherArray.getJSONObject(0);

                            // Get the int associated with "visibility"
                            int vis = response.getInt("visibility");

                            // Get the String associated with "name"
                            String name = response.getString("name");

                            // Get the String associated with "description"
                            String description = position0.getString("description");

                            // Get the String associated with "icon"
                            String iconName = position0.getString("icon");
                            // Get the JSONObject associated with "main"
                            JSONObject mainObject = response.getJSONObject("main");

                            // Get the temperature variables
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");


                            // Get the URL for the icon
                            String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";

                            // Make the ImageRequest
                            @SuppressLint("SetTextI18n") ImageRequest imgReq = new ImageRequest(imageUrl, image -> {

                                // Save the icon to the device
                                FileOutputStream fOut = null;
                                try {
                                    fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);

                                    image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    fOut.flush();
                                    fOut.close();
                                    // Get the ImageView from the layout
                                    ImageView iconImageView = findViewById(R.id.icon);

                                    // Set the downloaded image as the ImageView's image
                                    iconImageView.setImageBitmap(image);
                                    runOnUiThread( (  )  -> {
                                        binding.temp.setText("The current temperature is "+ current);
                                        binding.temp.setVisibility(View.VISIBLE);

                                        binding.maxTemp.setText("The max temperature is "+ max);
                                        binding.maxTemp.setVisibility(View.VISIBLE);

                                        binding.minTemp.setText("The min temperature is "+ min);
                                        binding.minTemp.setVisibility(View.VISIBLE);

                                        binding.icon.setImageBitmap(image);
                                        binding.icon.setVisibility(View.VISIBLE);

                                        binding.humidity.setText("The humidity is "+ humidity + "%");
                                        binding.humidity.setVisibility(View.VISIBLE);

                                        binding.description.setText(description);
                                        binding.description.setVisibility(View.VISIBLE);

                                    });

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error ) -> {
                                // Handle the error
                            });

                            // Add the ImageRequest to the RequestQueue
                            queue.add(imgReq);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        // Handle the error
                    }
            );

            queue.add(request);
        });
    }
}