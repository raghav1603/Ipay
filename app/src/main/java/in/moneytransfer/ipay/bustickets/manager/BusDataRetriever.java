package in.moneytransfer.ipay.bustickets.manager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.moneytransfer.ipay.bustickets.model.BusTickets;
import in.moneytransfer.ipay.bustickets.utils.BusDataListener;

/**
 * Created by raghav on 21/09/17.
 */

public class BusDataRetriever implements ValueEventListener {

    private BusDataListener busDataListener;
    private ArrayList<BusTickets> busTicketsArrayList;
    private Query query;
    public BusDataRetriever(BusDataListener busDataListener,String source) {
        this.busDataListener = busDataListener;

        busDataListener.onBusDataFetchStarted();

        busTicketsArrayList= new ArrayList<>();

        query = FirebaseDatabase.getInstance().getReference().child("busTickets")
                .orderByChild("busSource").equalTo(source);

        query.addValueEventListener(this);

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot getData : dataSnapshot.getChildren())
        {
            BusTickets tickets = getData.getValue(BusTickets.class);
            busTicketsArrayList.add(tickets);
        }

        busDataListener.onBusDataFetchCompleted(busTicketsArrayList);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        busDataListener.onBusDataCancelled();
    }
}
