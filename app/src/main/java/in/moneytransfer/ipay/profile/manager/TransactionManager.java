package in.moneytransfer.ipay.profile.manager;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.moneytransfer.ipay.Ipay;
import in.moneytransfer.ipay.home.model.Passbook;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

/**
 * Created by raghav on 22/09/17.
 */

public class TransactionManager implements ValueEventListener {

    private Double myAccountBalance;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private AccountBalanceListener balanceListener;
    private String dateOfTransaction;
    private Calendar calendar;

    public TransactionManager(AccountBalanceListener listener ) {
        balanceListener = listener;
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference().child("users").child(user.getUid());
        databaseReference.addValueEventListener(this);

        calendar = Calendar.getInstance();
        int mm = calendar.get(Calendar.MONTH)+1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int yy = calendar.get(Calendar.YEAR);

        dateOfTransaction = dd+"/"+mm+"/"+yy;

    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String bal;
        if (dataSnapshot.exists())
        {
            MyProfile profile = dataSnapshot.getValue(MyProfile.class);
            {
                if (profile != null && profile.getUserBalance() != null) {
                    bal = profile.getUserBalance();
                    myAccountBalance = Double.valueOf(bal);
                    if (null != balanceListener) {
                        balanceListener.onAccountBalanceRetrieved(myAccountBalance);
                    }
                }
            }
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(Ipay.getmApplication(), "Database Error", Toast.LENGTH_SHORT).show();
    }
    public void updateAccountBalance(double initialbalance,double deductionAmount,String transactionType)
    {
        DatabaseReference mref = databaseReference;
        double updateBalance = initialbalance - deductionAmount;
        String bal = updateBalance + "";
        mref.child("userBalance").setValue(bal);

        Passbook passbook = new Passbook();
        passbook.setTransAmount(deductionAmount+"");
        passbook.setTransType(transactionType);
        passbook.setTransDate(dateOfTransaction);

        mref.child("passbook").push().setValue(passbook);
    }
}
