package in.moneytransfer.ipay.buysell.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.buysell.model.BuySell;
import in.moneytransfer.ipay.buysell.utils.BuySellConstants;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;

/**
 * Created by raghav on 16/09/17.
 */

public class BuySellItemsAdapter extends FirebaseRecyclerAdapter<BuySell,BuySellItemsViewHolder> {

    private Context context;
    private OnOffersClickListener onOffersClickListener;
    public BuySellItemsAdapter(Class<BuySell> modelClass, @LayoutRes int modelLayout, Class<BuySellItemsViewHolder> viewHolderClass, Query query,Context context) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.context = context;
    }

    public void setOnOffersClickListener(OnOffersClickListener onOffersClickListener) {
        this.onOffersClickListener = onOffersClickListener;
    }

    @Override
    protected void populateViewHolder(BuySellItemsViewHolder viewHolder, final BuySell model, int position) {
        viewHolder.bindBuySellItems(model,context);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(BuySellConstants.BUY_SELL_BUNDLE_ID,model);
                if (null != onOffersClickListener)
                {
                    onOffersClickListener.onOffersItemClicked(bundle);
                }
            }
        });
    }
}
