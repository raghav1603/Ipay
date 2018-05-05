package in.moneytransfer.ipay.buysell.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.activity.BuySellDetailsActivity;
import in.moneytransfer.ipay.buysell.fragment.BuySellAddFragment;
import in.moneytransfer.ipay.buysell.fragment.BuySellItemsFragment;

/**
 * Created by raghav on 16/09/17.
 */

public class BuySellFragmentManager {

    private Activity activity;

    public BuySellFragmentManager(Activity activity) {
        this.activity = activity;
    }
    public void loadBuySellItemsFragment()
    {
        activity.getFragmentManager().beginTransaction()
                .add(R.id.buy_sell_container,new BuySellItemsFragment())
                .commit();

    }
    public void loadBuySellDetailsFragment(Bundle bundle)
    {
        Intent intent = new Intent(activity, BuySellDetailsActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    public void loadAddBuySellItemFragment()
    {
        activity.getFragmentManager()
                .beginTransaction()
                .replace(R.id.buy_sell_container,new BuySellAddFragment())
                .addToBackStack(null)
                .commit();

    }
}
