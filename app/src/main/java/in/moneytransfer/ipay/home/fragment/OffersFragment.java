package in.moneytransfer.ipay.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import in.moneytransfer.ipay.profile.manager.DeliveryFragmentManager;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class OffersFragment extends Fragment implements DataProgressListener,OnOffersClickListener,OnBuyAddCartClickListener {

    private RecyclerView offersRecycler;
    private ProgressBar offersProgressBar;
    private DataRetriever dataRetriever;
    private OffersAdapter adapter;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        offersProgressBar = view.findViewById(R.id.offers_progress_bar);
        offersRecycler = view.findViewById(R.id.offers_recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        offersRecycler.setLayoutManager(manager);

        dataRetriever = new DataRetriever(this,getActivity(), Constants.OFFERS_DATABASE_REFERENCE);

        this.adapter = dataRetriever.getAdapter();
        offersRecycler.setAdapter(adapter);

        adapter.setOnOffersClickListener(this);
        adapter.setOnBuyAddCartClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }

    @Override
    public void onFetchStarted() {
        offersProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchCompleted() {
        offersProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchCancelled() {
        offersProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onOffersItemClicked(Bundle bundle) {
        Intent intent = new Intent(getActivity(), OnClickLatestOffersActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void onAddToCartClicked(Bundle bundle) {

        LatestOffers offers = bundle.getParcelable(Constants.addToCartBundleId);

        DatabaseReference mref = databaseReference.child("cart");
        String key = mref.push().getKey();
        offers.setItemRef(key);

        mref.child(key).setValue(offers);

        Toast.makeText(getActivity(), "Item added to Cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBuyNowClicked(Bundle bundle) {
        Intent intent = new Intent(getActivity(), MyDeliveryActivity.class);
        intent.putExtras(bundle);
        intent.putExtra(ProfileConstants.FRAGMENT_CHOICE,2);
        startActivity(intent);
    }
}
