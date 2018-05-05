package in.moneytransfer.ipay.bustickets.utils;

import java.util.ArrayList;

import in.moneytransfer.ipay.bustickets.model.BusTickets;

/**
 * Created by raghav on 21/09/17.
 */

public interface BusDataListener {

    void onBusDataFetchStarted();
    void onBusDataFetchCompleted(ArrayList<BusTickets> ticketsArrayList);
    void onBusDataCancelled();
}
