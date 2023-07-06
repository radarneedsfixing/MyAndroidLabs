package algonquin.cst2335.kuma0194;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * MainActivity is the entry point of the application.
 * It contains an EditText and a Button to check the strength of a password.
 */
public class MainActivity extends AppCompatActivity {

    private TextView requirementsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et = findViewById(R.id.editText);
        Button btn = findViewById(R.id.button);
        requirementsTextView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et.getText().toString();
                checkPasswordStrength(password);
            }
        });
    }

    /**
     * Checks the strength of the password based on certain requirements.
     * Updates the requirementsTextView to indicate any missing requirements.
     *
     * @param password The password to be checked.
     */
    private void checkPasswordStrength(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialSymbol = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else if (isSpecialSymbol(ch)) {
                hasSpecialSymbol = true;
            }
        }

        boolean isStrong = hasUpperCase && hasLowerCase && hasNumber && hasSpecialSymbol;

        if (isStrong) {
            requirementsTextView.setText("Your password meets all the requirements");
        } else {
            requirementsTextView.setText("You shall not pass!");
        }
    }


    /**
     * Checks if a character is a special symbol.
     *
     * @param ch The character to be checked.
     * @return true if the character is a special symbol, false otherwise.
     */
    private boolean isSpecialSymbol(char ch) {
        String specialSymbols = "#$%^&*!@?";
        return specialSymbols.indexOf(ch) != -1;
    }
}
