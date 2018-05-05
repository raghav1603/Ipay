package in.moneytransfer.ipay.launch.utils;

import android.app.Activity;
import android.content.Intent;

import in.moneytransfer.ipay.home.activity.HomeActivity;
import in.moneytransfer.ipay.launch.activity.SignInActivity;
import in.moneytransfer.ipay.launch.activity.SignUpActivity;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public class LaunchManager {

    public static void loadHomeScreen(Activity activity)
    {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void loadSignUpScreen(Activity activity)
    {
        Intent intent = new Intent(activity, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
    public static void loadSignInScreen(Activity activity)
    {
        Intent intent = new Intent(activity,SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }
}
