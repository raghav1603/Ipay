package in.moneytransfer.ipay.home.manager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.activity.AddMoneyActivity;
import in.moneytransfer.ipay.home.activity.MapsActivity;
import in.moneytransfer.ipay.home.activity.PayActivity;
import in.moneytransfer.ipay.home.fragment.AcceptPaymentFragment;
import in.moneytransfer.ipay.home.fragment.PassbookFragment;

/**
 * Created by raghav on 14/08/17.
 */

public class OptionsFragmentManager {

    private Activity activity;
    private Fragment fragment;

    public OptionsFragmentManager(Activity activity) {
        this.activity = activity;
    }
    public void selectFragment(int choice)
    {
        switch (choice)
        {
            case 0:
            {
                Intent intent = new Intent(activity, PayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.finish();
                activity.startActivity(intent);
                break;
            }
            case 1:
            {
                Intent intent = new Intent(activity, AddMoneyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.finish();
                activity.startActivity(intent);

                break;
            }
            case 2:
            {
                fragment = new PassbookFragment();
                loadFragment(fragment);
                break;
            }
            case  3:
            {
                fragment = new AcceptPaymentFragment();
                loadFragment(fragment);
                break;
            }
            case 4:
            {
                Intent intent = new Intent(activity, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.finish();
                activity.startActivity(intent);

                break;
            }
            default:
                break;
        }
    }
    private void loadFragment(Fragment fragment)
    {
        activity.getFragmentManager().beginTransaction()
                .add(R.id.onclick_options_container,fragment)
                .commit();

    }
}
