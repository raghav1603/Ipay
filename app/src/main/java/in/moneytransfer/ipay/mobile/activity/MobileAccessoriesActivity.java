package in.moneytransfer.ipay.mobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.activity.OnClickLatestOffersActivity;
import in.moneytransfer.ipay.home.adapter.OffersAdapter;
import in.moneytransfer.ipay.home.manager.DataRetriever;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.home.utils.DataProgressListener;
import in.moneytransfer.ipay.home.utils.OnBuyAddCartClickListener;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;
import in.moneytransfer.ipay.profile.activity.MyDeliveryActivity;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class MobileAccessoriesActivity extends AppCompatActivity implements OnOffersClickListener,
        OnBuyAddCartClickListener,DataProgressListener {

    private RecyclerView electronicsRecycler;
    private ProgressBar electronicsProgress;
    private DataRetriever dataRetriever;
    private OffersAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_recharge);
        createUI();
    }
    private void createUI()
    {
        electronicsRecycler = findViewById(R.id.electronics_recycler);
        electronicsProgress = findViewById(R.id.electronics_progress);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        electronicsRecycler.setLayoutManager(manager);

        dataRetriever = new DataRetriever(this,this, Constants.OFFERS_DATABASE_REFERENCE);

        this.adapter = dataRetriever.getAdapter();
        electronicsRecycler.setAdapter(adapter);

        adapter.setOnBuyAddCartClickListener(this);
        adapter.setOnOffersClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }

    @Override
    public void onOffersItemClicked(Bundle bundle) {
        Intent intent = new Intent(this, OnClickLatestOffersActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClicked(Bundle bundle) {
        LatestOffers offers = bundle.getParcelable(Constants.addToCartBundleId);

        DatabaseReference mRef = databaseReference.child("cart");
        mRef.push().setValue(offers);

        Toast.makeText(this, "Item added to Cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBuyNowClicked(Bundle bundle) {
        Intent intent = new Intent(this, MyDeliveryActivity.class);
        intent.putExtras(bundle);
        intent.putExtra(ProfileConstants.FRAGMENT_CHOICE,2);
        startActivity(intent);
    }
    @Override
    public void onFetchStarted() {
        electronicsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchCompleted() {
        electronicsProgress.setVisibility(View.GONE);
    }

    @Override
    public void onFetchCancelled() {
        electronicsProgress.setVisibility(View.GONE);
    }
}
