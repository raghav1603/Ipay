package in.moneytransfer.ipay.profile.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;
import in.moneytransfer.ipay.profile.utils.DeleteCartItemListener;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

/**
 * Created by raghav on 14/09/17.
 */

public class CartRecyclerAdapter extends FirebaseRecyclerAdapter<LatestOffers,CartViewHolder> {


    private Context context;
    private OnOffersClickListener onOffersClickListener;
    private DeleteCartItemListener deleteCartItemListener;

    public CartRecyclerAdapter(Class<LatestOffers> modelClass, @LayoutRes int modelLayout, Class<CartViewHolder> viewHolderClass, Query query, Context context) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.context = context;
    }

    public void setOnOffersClickListener(OnOffersClickListener onOffersClickListener) {
        this.onOffersClickListener = onOffersClickListener;
    }

    public void setDeleteCartItemListener(DeleteCartItemListener deleteCartItemListener) {
        this.deleteCartItemListener = deleteCartItemListener;
    }

    @Override
    protected void populateViewHolder(CartViewHolder viewHolder, final LatestOffers model, int position) {
        viewHolder.bindCartData(model, context);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!= onOffersClickListener) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.buyNowBundleId, model);
                    onOffersClickListener.onOffersItemClicked(bundle);
                }

            }
        });
        viewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != deleteCartItemListener)
                {
                    deleteCartItemListener.deleteItemRef(model.getItemRef());
                }

            }
        });
    }
}
