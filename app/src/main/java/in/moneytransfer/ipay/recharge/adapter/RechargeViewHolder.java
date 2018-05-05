package in.moneytransfer.ipay.recharge.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.recharge.model.RechargeOffers;

/**
 * Created by raghav on 27/09/17.
 */

public class RechargeViewHolder extends RecyclerView.ViewHolder {

    private TextView rechargePrice,rechargeValidity,rechargeDescription;
    View view;
    public RechargeViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        rechargePrice = itemView.findViewById(R.id.recharge_price);
        rechargeValidity = itemView.findViewById(R.id.recharge_validity);
        rechargeDescription = itemView.findViewById(R.id.recharge_desc);
    }
    public void bindRechargeData(RechargeOffers offers)
    {
        rechargeDescription.setText("Description "+offers.getRechargeDesc());
        rechargeValidity.setText("Validity "+offers.getRechargeValidity());
        rechargePrice.setText("Rs. "+offers.getRechargePrice());
    }
}
