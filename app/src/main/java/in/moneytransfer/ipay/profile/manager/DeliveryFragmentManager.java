package in.moneytransfer.ipay.profile.manager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.profile.fragment.AddDeliveryAddressFragment;
import in.moneytransfer.ipay.profile.fragment.DeliverProductFragment;

/**
 * Created by raghav on 15/09/17.
 */

public class DeliveryFragmentManager {
    private Activity activity;

    public DeliveryFragmentManager(Activity activity) {
        this.activity = activity;
    }

    public void loadAddDeliveryFragment()
    {
        Fragment fragment = new AddDeliveryAddressFragment();

        activity.getFragmentManager().beginTransaction()
                .add(R.id.delivery_container,fragment)
                .commit();
    }
    public void loadDeliverFragment(Bundle bundle)
    {
        Fragment fragment = new DeliverProductFragment();
        fragment.setArguments(bundle);

        activity.getFragmentManager()
                .beginTransaction()
                .add(R.id.delivery_container,fragment)
                .commit();

    }
}
