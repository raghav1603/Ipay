package in.moneytransfer.ipay.launch.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.google.firebase.auth.FirebaseUser;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.launch.manager.FirebaseAuthCompleteListener;
import in.moneytransfer.ipay.launch.manager.FirebaseAuthManager;
import in.moneytransfer.ipay.launch.utils.LaunchManager;

public class SplashActivity extends AppCompatActivity implements FirebaseAuthCompleteListener {

    private Handler handler;
    private int POST_DELAYED = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuthManager manager = new FirebaseAuthManager(SplashActivity.this);
                manager.checkUserAuth();
            }
        },POST_DELAYED);

    }

    @Override
    public void onAuthSuccess(FirebaseUser user) {
        LaunchManager.loadHomeScreen(this);
    }

    @Override
    public void onAuthFailure() {
        LaunchManager.loadSignInScreen(this);

    }
}
