package in.moneytransfer.ipay.profile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.profile.manager.DeliveryFragmentManager;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class MyDeliveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_delivery);

        int choice = getIntent().getIntExtra(ProfileConstants.FRAGMENT_CHOICE,0);
        Bundle bundle = getIntent().getExtras();

       switch (choice)
       {
           case 1:
           {
               DeliveryFragmentManager manager = new DeliveryFragmentManager(this);
               manager.loadAddDeliveryFragment();
               break;
           }
           case 2:
           {
               DeliveryFragmentManager manager = new DeliveryFragmentManager(this);
               manager.loadDeliverFragment(bundle);
               break;
           }
           default:
               Toast.makeText(this, "Error Loading.", Toast.LENGTH_SHORT).show();
               break;
       }
    }
}
