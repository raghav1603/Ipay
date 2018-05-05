package in.moneytransfer.ipay.profile.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.launch.manager.SharedPrefsManager;
import in.moneytransfer.ipay.launch.utils.FingerPrintConstants;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class MyProfileActivity extends AppCompatActivity implements
        ValueEventListener,View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private TextView email, username;
    private EditText phoneNumber;
    private Button confirmButton, logout;
    private DatabaseReference databaseReference;
    private String userEmailId, userUniqueId;
    private DatabaseReference checkRef;
    private String userName;
    private TextView deliveryAddress;
    private TextView profileBalance;
    private Switch fingerPrintSwitch;
    private SharedPrefsManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        createUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            detectHardware();
        }

    }

    private void createUI() {
        email = findViewById(R.id.profile_email);
        username = findViewById(R.id.profile_username);
        phoneNumber = findViewById(R.id.profile_phone);
        deliveryAddress = findViewById(R.id.profile_delivery);
        confirmButton = findViewById(R.id.confirm_number);
        logout = findViewById(R.id.profile_logout);
        profileBalance = findViewById(R.id.profile_balance);
        fingerPrintSwitch = findViewById(R.id.switch1);
        fingerPrintSwitch.setOnCheckedChangeListener(this);
        fingerPrintSwitch.setVisibility(View.GONE);

        confirmButton.setOnClickListener(this);
        logout.setOnClickListener(this);
        deliveryAddress.setOnClickListener(this);

        manager = new SharedPrefsManager(this);
        boolean printStatus = manager.getValue(FingerPrintConstants.printKey);

        if (printStatus)
        {
            fingerPrintSwitch.setChecked(true);
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            userEmailId = user.getEmail();
            userUniqueId = user.getUid();
            userName = user.getDisplayName();
        }

        email.setText(userEmailId);
        username.setText(userName);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        checkRef = databaseReference.child(userUniqueId);
        checkRef.keepSynced(true);
        checkRef.addValueEventListener(this);

    }

    private void confirmPhoneNumber() {
        final String phoneNo = phoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNo) || phoneNo.length() > 10 || phoneNo.length() < 10) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }
        disableButton();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNo, 60, TimeUnit.SECONDS, MyProfileActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                MyProfile profile = new MyProfile(userEmailId, phoneNo, userUniqueId, 100.0 + "",userName);

                HashMap<String, Object> res = (HashMap<String, Object>) profile.toMap();
                DatabaseReference uid = databaseReference.child(userUniqueId);
                uid.setValue(res);

                Toast.makeText(MyProfileActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                enableButton();
                Toast.makeText(MyProfileActivity.this, "Verification Failed.Retry after some Time", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.items_cart: {
                startActivity(new Intent(this, MyCartActivity.class));
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            MyProfile profile = dataSnapshot.getValue(MyProfile.class);
            if (profile.getUserEmailId() != null) {
                phoneNumber.setText(profile.getUserPhoneNumber());
                profileBalance.setText("Rs. " + profile.getUserBalance());
                disableButton();
            }
        } else {
            enableButton();
            Toast.makeText(this, "No user exists", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.confirm_number:
            {
                confirmPhoneNumber();
                break;
            }
            case R.id.profile_logout:
            {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                break;
            }
            case R.id.profile_delivery:
            {
                Intent intent = new Intent(this,MyDeliveryActivity.class);
                intent.putExtra(ProfileConstants.FRAGMENT_CHOICE,1);
                startActivity(intent);
            }
            default:
                break;
        }
    }
    private void enableButton()
    {
        phoneNumber.setEnabled(true);
        confirmButton.setVisibility(View.VISIBLE);

    }
    private void disableButton()
    {
        phoneNumber.setEnabled(false);

        confirmButton.setVisibility(View.GONE);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (b)
        {
            manager.addValue(FingerPrintConstants.printKey,true);
        }
        else {
            manager.addValue(FingerPrintConstants.printKey,false);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void detectHardware()
    {
        FingerprintManager manager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (manager.isHardwareDetected())
        {
            fingerPrintSwitch.setVisibility(View.VISIBLE);
        }
    }
}
