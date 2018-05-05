package in.moneytransfer.ipay.recharge.utils;

import java.util.ArrayList;

import in.moneytransfer.ipay.recharge.model.RechargeOffers;

/**
 * Created by raghav on 24/09/17.
 */

public interface RechargeOffersDataListener {

    void onOffersFetchStarted();
    void onOffersFetchCompleted(ArrayList<RechargeOffers> offers);
    void onOffersFetchCancelled();
}
