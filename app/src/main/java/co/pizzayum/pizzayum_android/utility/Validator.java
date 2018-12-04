package co.pizzayum.pizzayum_android.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    /**
     * validate your email address
     */
    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean passwordValidator(String password) {
        if (password.length() > 5) {
            return true;
        } else {
            return false;
        }
    }
}
