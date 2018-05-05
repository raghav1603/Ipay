package in.moneytransfer.ipay.home.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.Passbook;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;


public class PayByPhoneFragment extends Fragment implements View.OnClickListener
        ,ChildEventListener,AccountBalanceListener {

    private EditText sendByAmount,sendByNumber;
    private TextView sendByName;
    private Button sendByVerify,sendByMoney;
    private DatabaseReference databaseReference;
    private Query query;
    private TransactionManager manager;
    private Double remBalance,transferAmount;
    private MyProfile profile;
    private Calendar calendar;
    private String dateOfTransaction;
    private FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay_by_phone, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendByAmount = view.findViewById(R.id.send_by_amount);
        sendByNumber = view.findViewById(R.id.send_by_phone_number);
        sendByName = view.findViewById(R.id.send_by_name);
        sendByName.setVisibility(View.GONE);

        sendByVerify = view.findViewById(R.id.send_by_verify_button);
        sendByVerify.setOnClickListener(this);

        sendByMoney = view.findViewById(R.id.send_by_send_money);
        sendByMoney.setOnClickListener(this);
        sendByMoney.setVisibility(View.GONE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        manager = new TransactionManager(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        makeDate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.send_by_verify_button:
            {
                findUser();
                sendByName.setVisibility(View.VISIBLE);
                sendByName.setText("No Such User Found");
                break;
            }
            case R.id.send_by_send_money:
            {
                transferAmount = Double.valueOf(sendByAmount.getText().toString().trim());

                if (remBalance >0 && remBalance>=transferAmount) {
                    manager.updateAccountBalance(remBalance, transferAmount, "Sent to " + profile.getUserDisplayName());
                    updateReceiverDetails(sendByAmount.getText().toString().trim(), transferAmount);
                    break;
                }
                else {
                    return;
                }
            }
            default:
                break;
        }
    }

    private void findUser() {
        String phoneNumber = sendByNumber.getText().toString().trim();
        query=  databaseReference.orderByChild("userPhoneNumber").equalTo(phoneNumber);
        query.limitToFirst(1);
        query.addChildEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot.exists())
        {
            Toast.makeText(getActivity(), "user found", Toast.LENGTH_SHORT).show();
            profile = dataSnapshot.getValue(MyProfile.class);
            sendByName.setVisibility(View.VISIBLE);
            sendByName.setText(profile.getUserDisplayName());

            sendByVerify.setVisibility(View.GONE);
            sendByMoney.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccountBalanceRetrieved(double balance) {
        remBalance = balance;
    }

    private void updateReceiverDetails(String tAmount,Double transAmount)
    {
        Passbook passbook = new Passbook();
        passbook.setTransDate(dateOfTransaction);
        passbook.setTransAmount(tAmount);
        passbook.setTransType("Received from "+ user.getDisplayName());

        Double receiverAmount = Double.valueOf(profile.getUserBalance())+ transAmount;
        String recRef = profile.getUserUid();
        String updatedBalance = String.valueOf(receiverAmount);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(recRef);
        ref.child("passbook").push().setValue(passbook);

        ref.child("userBalance").setValue(updatedBalance);

    }
    private void makeDate()
    {
        calendar = Calendar.getInstance();
        int mm = calendar.get(Calendar.MONTH)+1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int yy = calendar.get(Calendar.YEAR);

        dateOfTransaction = dd+"/"+mm+"/"+yy;
    }
}
