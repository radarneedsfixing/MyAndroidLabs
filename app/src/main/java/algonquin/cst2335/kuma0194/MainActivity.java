package algonquin.cst2335.kuma0194;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected EditText theEditText;
    protected Toolbar toolbar;
    protected TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        theEditText = findViewById(R.id.theEditText);
        textView = findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // ... (rest of the code)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            Toast.makeText(this, "You clicked on Delete", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_reset) {
            Toast.makeText(this, "You clicked on Reset", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.menu_help) {
            Toast.makeText(this, "You clicked on Help", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
