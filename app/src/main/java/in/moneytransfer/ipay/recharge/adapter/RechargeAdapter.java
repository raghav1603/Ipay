package in.moneytransfer.ipay.recharge.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.recharge.model.RechargeOffers;
import in.moneytransfer.ipay.recharge.utils.RechargeOffersClickListener;

/**
 * Created by raghav on 27/09/17.
 */

public class RechargeAdapter extends FirebaseRecyclerAdapter<RechargeOffers,RechargeViewHolder> {

    private RechargeOffersClickListener rechargeOffersClickListener;
    public RechargeAdapter(Class<RechargeOffers> modelClass, @LayoutRes int modelLayout, Class<RechargeViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    public void setRechargeOffersClickListener(RechargeOffersClickListener rechargeOffersClickListener) {
        this.rechargeOffersClickListener = rechargeOffersClickListener;
    }

    @Override
    protected void populateViewHolder(RechargeViewHolder viewHolder, final RechargeOffers model, int position) {
        viewHolder.bindRechargeData(model);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != rechargeOffersClickListener)
                {
                    rechargeOffersClickListener.onRechargeOffersClicked(model);
                }
            }
        });
    }
}
