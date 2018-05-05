package in.moneytransfer.ipay.launch.activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import in.moneytransfer.ipay.Progress_bar;
import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.launch.manager.SharedPrefsManager;
import in.moneytransfer.ipay.launch.manager.SignUpManager;
import in.moneytransfer.ipay.launch.utils.FingerPrintConstants;
import in.moneytransfer.ipay.launch.utils.LaunchManager;
import in.moneytransfer.ipay.launch.utils.ValidationUtils;
import in.moneytransfer.ipay.profile.activity.MyProfileActivity;
import in.moneytransfer.ipay.profile.model.MyProfile;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,SignUpManager.SignUpCompleteListener {

    private EditText signUpEmail;
    private EditText signUpUsername;
    private EditText signUpPassword;
    private Button signUpButton;
    private SignUpManager signUpManager;
    private String email;
    private String password;
    private SharedPrefsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        createLayout();
    }
    private void createLayout()
    {
        signUpUsername = findViewById(R.id.signUp_username);
        signUpEmail =  findViewById(R.id.signUp_email);
        signUpPassword = findViewById(R.id.signUp_password);

        signUpButton = findViewById(R.id.signUp_button);
        signUpButton.setOnClickListener(this);

        manager = new SharedPrefsManager(this);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.signUp_button: {
                confirmEmail();
                break;
            }
        }
    }
    private void confirmEmail()
    {
        String username = signUpUsername.getText().toString().trim();
        email = signUpEmail.getText().toString().trim();
        password = signUpPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            signUpUsername.setError(getString(R.string.usernameErrorMessage));
            signUpUsername.requestFocus();
            return;
        }
        if (!ValidationUtils.isEmailValid(email)){
            signUpEmail.setError(getString(R.string.emailErrorMessage));
            signUpEmail.requestFocus();
            return;
        }
        if (! ValidationUtils.isPasswordValid(password)){
            signUpPassword.setError(getString(R.string.passwordErrorMessage));
            signUpPassword.requestFocus();
            return;
        }
        manager.addStringValue(FingerPrintConstants.userEmail,email);
        manager.addStringValue(FingerPrintConstants.userPassword,password);

        signUpManager = new SignUpManager(this);
        signUpManager.signUpUser(username,email,password);
        Progress_bar progressBar = new Progress_bar();
        progressBar.Showdialog("Signing up",this);

    }
    @Override
    public void onSignUpSuccess(FirebaseUser user) {
        LaunchManager.loadHomeScreen(this);
    }

    @Override
    public void onSignUpFailure() {
        Toast.makeText(this, "Sign In Error", Toast.LENGTH_SHORT).show();
    }
}
