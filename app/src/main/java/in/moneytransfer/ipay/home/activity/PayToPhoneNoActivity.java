package in.moneytransfer.ipay.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.fragment.PayByIdFragment;
import in.moneytransfer.ipay.home.fragment.PayByPhoneFragment;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

public class PayToPhoneNoActivity extends AppCompatActivity{

    private String moneyID = "a";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_to_phone_no);

        moneyID = getIntent().getStringExtra(Constants.PAY_DATA);
        if (moneyID.trim().length() > 1)
        {
            PayByIdFragment fragment = new PayByIdFragment();

            Bundle bundle = new Bundle();
            bundle.putString("dataForFragment",moneyID);
            fragment.setArguments(bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.send_money_container,fragment)
                    .commit();
        }
        else
        {
            PayByPhoneFragment fragment = new PayByPhoneFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.send_money_container,fragment)
                    .commit();
        }
    }

}
