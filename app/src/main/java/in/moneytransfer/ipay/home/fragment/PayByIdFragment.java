package in.moneytransfer.ipay.home.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.Passbook;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

public class PayByIdFragment extends Fragment implements
        View.OnClickListener,AccountBalanceListener,ValueEventListener {

    private Button sendMoney;
    private EditText amount;
    private Double remBalance;
    private DatabaseReference database;
    private String recID;
    private TransactionManager manager;
    private MyProfile profile;
    private Calendar calendar;
    private String dateOfTransaction;
    private FirebaseUser user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromBundle();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay_by_id, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amount = view.findViewById(R.id.amount_editText);

        sendMoney = view.findViewById(R.id.send_money_button);
        sendMoney.setOnClickListener(this);

        manager = new TransactionManager(this);

        database = FirebaseDatabase.getInstance().getReference().child("users").child(recID);
        database.addValueEventListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        makeDate();
    }
    private void getDataFromBundle() {

        Bundle b = getArguments();
        recID = b.getString("dataForFragment");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.send_money_button:
            {

                Double traAmount = Double.valueOf(amount.getText().toString().trim());
                if (remBalance > 0 && remBalance >=traAmount) {
                    manager.updateAccountBalance(remBalance, traAmount, "Sent To " + profile.getUserDisplayName());
                    updateReceiverDetails(amount.getText().toString().trim(), traAmount);
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

    @Override
    public void onAccountBalanceRetrieved(double balance) {
        remBalance = balance;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists())
        {
            profile = dataSnapshot.getValue(MyProfile.class);
        }
        else {
            Toast.makeText(getActivity(), "No Such User Found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    }
    private void updateReceiverDetails(String tAmount,Double transAmount)
    {
        Passbook passbook = new Passbook();
        passbook.setTransDate(dateOfTransaction);
        passbook.setTransAmount(tAmount);
        passbook.setTransType("Received from "+ user.getDisplayName());

        Double receiverAmount = Double.valueOf(profile.getUserBalance())+ transAmount;
        String updatedBalance = String.valueOf(receiverAmount);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(recID);
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
