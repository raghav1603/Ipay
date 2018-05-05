package in.moneytransfer.ipay.recharge.manager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.moneytransfer.ipay.bustickets.utils.BusDataListener;
import in.moneytransfer.ipay.recharge.model.RechargeOffers;
import in.moneytransfer.ipay.recharge.utils.RechargeOffersDataListener;

/**
 * Created by raghav on 24/09/17.
 */

public class RechargePlansDataRetriever implements ValueEventListener {

    private RechargeOffersDataListener rechargeOffersDataListener;
    private ArrayList<RechargeOffers> list;
    private Query query;

    public RechargePlansDataRetriever(RechargeOffersDataListener dataListener) {
        this.rechargeOffersDataListener = dataListener;
        dataListener.onOffersFetchStarted();

        list = new ArrayList<>();

        query = FirebaseDatabase.getInstance().getReference().child("rechargeOffers");
        query.addValueEventListener(this);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot getData : dataSnapshot.getChildren())
        {
            list.add(getData.getValue(RechargeOffers.class));
        }
        rechargeOffersDataListener.onOffersFetchCompleted(list);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        rechargeOffersDataListener.onOffersFetchCancelled();
    }
}
