package co.pizzayum.pizzayum_android.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.services.Authentication;
import co.pizzayum.pizzayum_android.utility.Validator;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    // Layout Views
    Button register_button;
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
        register_button = findViewById(R.id.register_button);

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

        register_button.setOnClickListener(this);
        signup_url.setOnClickListener(this);
        // initialise custom fonts
        initializeCustomFonts();
    }

    /**
     * MainActivity user after validation process
     */
    void registerUser() {
        String name = "";
        if (user_name.getText() != null) {
            name = user_name.getText().toString();
        }
        String email = user_email.getText().toString();
        String password = user_password.getText().toString();

        Validator validator = new Validator();

        if (!name.isEmpty()) {
            if (validator.emailValidator(email)) {
                if (validator.passwordValidator(password)) {
                    Authentication auth = new Authentication(Signup.this, 2, name,
                            email, password);
                    auth.networkCall();
                } else {
                    user_password.setError("password should be greater than 5");
                }
            } else {
                user_email.setError("Please Input Correct Email");
            }
        } else {
            user_name.setError("username required");
        }
    }

    /**
     * Assigning custom ofonts style and set that custom font to textViews
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                registerUser();
                break;
            case R.id.signup_url:
                startService(new Intent(this, Login.class));
                finish();
                break;
        }
    }
}
