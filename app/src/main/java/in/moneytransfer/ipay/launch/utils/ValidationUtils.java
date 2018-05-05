package in.moneytransfer.ipay.launch.utils;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public class ValidationUtils {

    private static final int PASSWORD_CHAR_LIMIT = 6;

    public static boolean isEmailValid(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean isPasswordValid(String password) {
        return (!TextUtils.isEmpty(password) && password.length() > PASSWORD_CHAR_LIMIT);
    }
}
