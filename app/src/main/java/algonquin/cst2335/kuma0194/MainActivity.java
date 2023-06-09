package algonquin.cst2335.kuma0194;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;
    private CheckBox checkBox;
    private Switch switchButton;
    private RadioButton radioButton;
    private ImageView imageView;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        checkBox = findViewById(R.id.checkBox);
        switchButton = findViewById(R.id.switchButton);
        radioButton = findViewById(R.id.radioButton);
        imageView = findViewById(R.id.imageView);
        imageButton = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = editText.getText().toString();
                textView.setText(newText);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "Checked" : "Unchecked";
                Toast.makeText(MainActivity.this, "Checkbox state: " + state, Toast.LENGTH_SHORT).show();
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "ON" : "OFF";
                Toast.makeText(MainActivity.this, "Switch state: " + state, Toast.LENGTH_SHORT).show();
            }
        });

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String state = isChecked ? "Selected" : "Unselected";
                Toast.makeText(MainActivity.this, "Radio button state: " + state, Toast.LENGTH_SHORT).show();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = imageButton.getWidth();
                int height = imageButton.getHeight();
                Toast.makeText(MainActivity.this, "ImageButton width: " + width + ", height: " + height, Toast.LENGTH_SHORT).show();
            }
        });
    }

}