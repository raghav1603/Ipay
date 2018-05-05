package in.moneytransfer.ipay.home.manager;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.adapter.OffersAdapter;
import in.moneytransfer.ipay.home.adapter.OffersViewHolder;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.home.utils.DataProgressListener;

/**
 * Created by raghav on 19/08/17.
 */

public class DataRetriever implements ChildEventListener,ValueEventListener {

    private DataProgressListener listener;
    private Context context;
    private OffersAdapter adapter;

    public DataRetriever(DataProgressListener listener, Context context,String databaseReference) {
        this.listener = listener;
        this.context = context;

        listener.onFetchStarted();

        Query query = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReference);
        query.keepSynced(true);

        query.addListenerForSingleValueEvent(this);
        query.addChildEventListener(this);


        adapter = new OffersAdapter(LatestOffers.class, R.layout.offers_row_layout, OffersViewHolder.class,query,context);
    }

    public OffersAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        adapter.notifyDataSetChanged();
        this.listener.onFetchCompleted();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        this.listener.onFetchCompleted();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        this.listener.onFetchCompleted();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        this.listener.onFetchCompleted();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        this.listener.onFetchCompleted();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        this.listener.onFetchCancelled();
    }
}
