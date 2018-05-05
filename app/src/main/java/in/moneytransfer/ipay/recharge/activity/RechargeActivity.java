package in.moneytransfer.ipay.recharge.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;
import in.moneytransfer.ipay.recharge.adapter.RechargeAdapter;
import in.moneytransfer.ipay.recharge.adapter.RechargeViewHolder;
import in.moneytransfer.ipay.recharge.model.RechargeOffers;
import in.moneytransfer.ipay.recharge.utils.RechargeOffersClickListener;

public class RechargeActivity extends AppCompatActivity implements
        View.OnClickListener,RechargeOffersClickListener,AccountBalanceListener {

    private Spinner circleSpinner,providerSpinner;
    private EditText amount;
    private Button rechargeNow;
    private ArrayAdapter<CharSequence> circleAdapter;
    private ArrayAdapter<CharSequence> providerAdapter;
    private String circleStr,providerStr;
    private RecyclerView rechargeRecycler;
    private Double accountBalance = 0.0;
    private TransactionManager transactionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initializeUI();
    }
    private void initializeUI()
    {
        circleSpinner = findViewById(R.id.recharge_select_circle);
        providerSpinner = findViewById(R.id.recharge_select_provider);
        amount = findViewById(R.id.recharge_select_amount);
        rechargeNow = findViewById(R.id.recharge_now);
        rechargeNow.setOnClickListener(this);

        rechargeRecycler = findViewById(R.id.recharge_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rechargeRecycler.setLayoutManager(manager);

        transactionManager = new TransactionManager(this);

        circleAdapter = ArrayAdapter.createFromResource(this,R.array.circle_list,android.R.layout.simple_spinner_item);
        circleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        circleSpinner.setAdapter(circleAdapter);
        circleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                circleStr = (String) adapterView.getItemAtPosition(i);
                if (!circleStr.equals("Select Circle"))
                {
                    providerSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(RechargeActivity.this, "Select Circle", Toast.LENGTH_SHORT).show();
            }
        });

        providerAdapter = ArrayAdapter.createFromResource(this,R.array.providers_list,android.R.layout.simple_spinner_item);
        providerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        providerSpinner.setAdapter(providerAdapter);
        providerSpinner.setVisibility(View.INVISIBLE);
        providerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                providerStr = (String) adapterView.getItemAtPosition(i);
                if (providerStr.compareTo("Select Service Provider") != 0)
                {
                    setUpAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(RechargeActivity.this, "Select Provider", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        String amt = amount.getText().toString().trim();
        Double val = Double.valueOf(amt);
        if (accountBalance >= val)
        {
            transactionManager.updateAccountBalance(accountBalance,val,"Recharge done");
            Toast.makeText(this, "Recharge Successful", Toast.LENGTH_SHORT).show();
            amount.setText("");

        }
        else {
            Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRechargeOffersClicked(RechargeOffers rechargeOffers) {
        amount.setText(rechargeOffers.getRechargePrice());
    }
    private void setUpAdapter()
    {
        Query query = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ipay-385ad.firebaseio.com/rechargeOffers");
        RechargeAdapter adap = new RechargeAdapter(RechargeOffers.class,R.layout.row_layout_recharge_offers, RechargeViewHolder.class,query);
        rechargeRecycler.setAdapter(adap);
        adap.setRechargeOffersClickListener(this);
    }

    @Override
    public void onAccountBalanceRetrieved(double balance) {
        accountBalance = balance;
    }
}
