package co.pizzayum.pizzayum_android.classes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.pizzayum.pizzayum_android.R;

public class Signup extends AppCompatActivity {

    // Layout Views
    Button login_button;
    TextView email_label, password_label, continue_with_label, fb_button_label,
            google_button_label, signup_url, username_label;
    EditText user_email, user_password, user_name;
    LinearLayout fb_login, google_login;

    Typeface customFontStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_signup);

        // initialize layout views
        // Button
        login_button = findViewById(R.id.login_button);

        // TextViews
        password_label = findViewById(R.id.password_label);
        email_label = findViewById(R.id.email_label);
        continue_with_label = findViewById(R.id.continue_with_label);
        fb_button_label = findViewById(R.id.fb_button_label);
        google_button_label = findViewById(R.id.google_button_label);
        signup_url = findViewById(R.id.signup_url);
        username_label = findViewById(R.id.username_label);

        // EditTexts
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        user_name = findViewById(R.id.user_name);

        // Linear Layouts
        fb_login = findViewById(R.id.fb_login);
        google_login = findViewById(R.id.google_login);

        // initialise custom fonts
        initializeCustomFonts();
    }


    /**
     * Assigning custom fonts style and set that custom font to textViews
     */
    void initializeCustomFonts() {
        customFontStyle = Typeface.createFromAsset(getAssets(), "fonts/Athiti-Regular.ttf");

        // textViews
        password_label.setTypeface(customFontStyle);
        email_label.setTypeface(customFontStyle);
        continue_with_label.setTypeface(customFontStyle);
        user_name.setTypeface(customFontStyle);

        // EditTexts
        user_email.setTypeface(customFontStyle);
        user_password.setTypeface(customFontStyle);
        username_label.setTypeface(customFontStyle);

        customFontStyle = Typeface.createFromAsset(getAssets(), "fonts/Athiti-SemiBold.ttf");
        fb_button_label.setTypeface(customFontStyle);
        google_button_label.setTypeface(customFontStyle);

    }


}
