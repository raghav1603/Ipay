package in.moneytransfer.ipay.womensfashion.activity;

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
import in.moneytransfer.ipay.home.utils.OnRecyclerViewItemClickListener;
import in.moneytransfer.ipay.profile.activity.MyDeliveryActivity;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;
import in.moneytransfer.ipay.womensfashion.utils.WomensFashionConstant;

public class WomensFashionActivity extends AppCompatActivity implements DataProgressListener,
        OnOffersClickListener,OnBuyAddCartClickListener {

    private RecyclerView womensRecycler;
    private ProgressBar progressBar;
    private DataRetriever dataRetriever;
    private OffersAdapter adapter;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womens_fashion);

        createUI();
    }
    private void createUI()
    {
        womensRecycler = findViewById(R.id.womens_fashion_recycler);
        progressBar = findViewById(R.id.womens_fashion_progress);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        womensRecycler.setLayoutManager(manager);

        dataRetriever = new DataRetriever(this,this, WomensFashionConstant.WOMENS_FASHION_DATABASE_REFERENCE);

        this.adapter = dataRetriever.getAdapter();
        womensRecycler.setAdapter(adapter);

        adapter.setOnBuyAddCartClickListener(this);
        adapter.setOnOffersClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }

    @Override
    public void onFetchStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchCompleted() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchCancelled() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onAddToCartClicked(Bundle bundle) {
        LatestOffers offers = bundle.getParcelable(Constants.addToCartBundleId);

        DatabaseReference mref = databaseReference.child("cart");
        String key = mref.push().getKey();
        offers.setItemRef(key);

        mref.child(key).setValue(offers);

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
    public void onOffersItemClicked(Bundle bundle) {
        Intent intent = new Intent(this, OnClickLatestOffersActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
