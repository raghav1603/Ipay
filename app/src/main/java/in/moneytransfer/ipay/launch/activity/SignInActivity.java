package in.moneytransfer.ipay.launch.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import in.moneytransfer.ipay.Progress_bar;
import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.launch.fragment.GmailLoginFragment;
import in.moneytransfer.ipay.launch.manager.FingerprintHandler;
import in.moneytransfer.ipay.launch.manager.SharedPrefsManager;
import in.moneytransfer.ipay.launch.manager.SignInManager;
import in.moneytransfer.ipay.launch.utils.FingerPrintConstants;
import in.moneytransfer.ipay.launch.utils.FingerPrintListener;
import in.moneytransfer.ipay.launch.utils.LaunchManager;
import in.moneytransfer.ipay.launch.utils.ValidationUtils;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener,SignInManager.SignInCompleteListener,FingerPrintListener {

    private EditText emailSignIn;
    private EditText passwordSignIn;
    private Button signInButton;
    private TextView signUpTextView;
    private SignInManager signInManager;
    private SharedPrefsManager manager;
    private String email,password;
    private Cipher cipher;
    private KeyStore keyStore;
    private TextView forgotpassword;
    private static final String KEY_NAME = "fingerPrint";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        createUI();
        checkFingerPrint();
    }
    private void createUI()
    {
        emailSignIn = findViewById(R.id.signIn_email);
        passwordSignIn =  findViewById(R.id.signIn_password);
        signInButton = findViewById(R.id.signIn_button);
        signUpTextView = findViewById(R.id.signUp_txtMsg);
        manager = new SharedPrefsManager(this);


        forgotpassword = findViewById(R.id.signIn_forgot);
        forgotpassword.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        signUpTextView.setOnClickListener(this);

        getFragmentManager().beginTransaction()
                .add(R.id.gmail_login_container,new GmailLoginFragment())
                .commit();

        signInManager = new SignInManager(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signIn_button:
                email = emailSignIn.getText().toString().trim();
                password = passwordSignIn.getText().toString().trim();

                if (!ValidationUtils.isEmailValid(email)){
                    emailSignIn.setError(getString(R.string.emailErrorMessage));
                    emailSignIn.requestFocus();
                    return;
                }
                if (! ValidationUtils.isPasswordValid(password)) {
                    passwordSignIn.setError(getString(R.string.passwordErrorMessage));
                    passwordSignIn.requestFocus();
                    return;
                }

                signInManager.signInUser(email,password);
                Progress_bar progressBar = new Progress_bar();
                progressBar.Showdialog("Signing in",this);



                break;
            case R.id.signUp_txtMsg:
                LaunchManager.loadSignUpScreen(this);
                break;
            case R.id.signIn_forgot:
            {
                email = emailSignIn.getText().toString().trim();
                if (!ValidationUtils.isEmailValid(email)){
                    emailSignIn.setError(getString(R.string.emailErrorMessage));
                    emailSignIn.requestFocus();
                    return;
                }
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email);
            }
            default:
                break;
        }
    }
    private void checkFingerPrint()
    {
        boolean status = manager.getValue(FingerPrintConstants.printKey);
        if (status)
        {
            Toast.makeText(this, "FingerPrint Enabled", Toast.LENGTH_SHORT).show();
            startFingerPrint();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void startFingerPrint()
    {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (!fingerprintManager.isHardwareDetected())
            Toast.makeText(this, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
        else {
            if (!fingerprintManager.hasEnrolledFingerprints())
                Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
            else {
                if (!keyguardManager.isKeyguardSecure())
                    Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                else
                    genKey();

                if (cipherInit()) {
                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this,this);
                    helper.startAuthentication(fingerprintManager, cryptoObject);
                }
            }
        }

    }
    @TargetApi(Build.VERSION_CODES.M)
    private boolean cipherInit() {

        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey)keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (IOException e1) {

            e1.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e1) {

            e1.printStackTrace();
            return false;
        } catch (CertificateException e1) {

            e1.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException e1) {

            e1.printStackTrace();
            return false;
        } catch (KeyStoreException e1) {

            e1.printStackTrace();
            return false;
        } catch (InvalidKeyException e1) {

            e1.printStackTrace();
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void genKey() {

        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT
                    | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build()
            );
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onSignInSuccess(FirebaseUser user) {
        LaunchManager.loadHomeScreen(this);
    }

    @Override
    public void onSignInFailure() {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFingerPrintAuthenticated() {

        Progress_bar progressBar = new Progress_bar();
        progressBar.Showdialog("Signing in",this);

        String eMail = manager.getStringValue(FingerPrintConstants.userEmail);
        String pwd = manager.getStringValue(FingerPrintConstants.userPassword);

        signInManager.signInUser(eMail,pwd);
    }
}
