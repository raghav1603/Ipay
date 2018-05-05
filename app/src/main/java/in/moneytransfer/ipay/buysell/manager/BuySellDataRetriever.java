package in.moneytransfer.ipay.buysell.manager;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.adapter.BuySellItemsAdapter;
import in.moneytransfer.ipay.buysell.adapter.BuySellItemsViewHolder;
import in.moneytransfer.ipay.buysell.model.BuySell;
import in.moneytransfer.ipay.buysell.utils.BuySellConstants;
import in.moneytransfer.ipay.home.utils.DataProgressListener;

/**
 * Created by raghav on 16/09/17.
 */

public class BuySellDataRetriever implements ChildEventListener,ValueEventListener {

    private DataProgressListener dataProgressListener;
    private Context context;
    private BuySellItemsAdapter adapter;

    public BuySellDataRetriever(DataProgressListener dataProgressListener, Context context) {
        this.dataProgressListener = dataProgressListener;
        this.context = context;

        dataProgressListener.onFetchStarted();

        Query query = FirebaseDatabase.getInstance().getReferenceFromUrl(BuySellConstants.BUY_SELL_DATABASE_REFERENCE);
        query.addChildEventListener(this);
        query.addListenerForSingleValueEvent(this);
        adapter = new BuySellItemsAdapter(BuySell.class, R.layout.buy_sell_row_layout, BuySellItemsViewHolder.class,query,context);
    }

    public BuySellItemsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        adapter.notifyDataSetChanged();
        dataProgressListener.onFetchCompleted();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        dataProgressListener.onFetchCompleted();
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        dataProgressListener.onFetchCompleted();
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        dataProgressListener.onFetchCompleted();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        dataProgressListener.onFetchCompleted();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        dataProgressListener.onFetchCancelled();
    }
}
