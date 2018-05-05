package in.moneytransfer.ipay.buysell.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.manager.BuySellFragmentManager;

public class BuySellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell);

        BuySellFragmentManager manager = new BuySellFragmentManager(this);
        manager.loadBuySellItemsFragment();
    }
}
