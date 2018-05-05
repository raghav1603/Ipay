package in.moneytransfer.ipay.home.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.home.utils.OnBuyAddCartClickListener;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;

/**
 * Created by raghav on 19/08/17.
 */

public class OffersAdapter extends FirebaseRecyclerAdapter<LatestOffers,OffersViewHolder> {
    /**
     * @param modelClass      Firebase will marshall the data at a location into
     *                        an instance of a class that you provide
     * @param modelLayout     This is the layout used to represent a single item in the list.
     *                        You will be responsible for populating an instance of the corresponding
     *                        view with the data from an instance of modelClass.
     * @param viewHolderClass The class that hold references to all sub-views in an instance modelLayout.
     * @param ref             The Firebase location to watch for data changes. Can also be a slice of a location,
     *                        using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */
    private Context context;
    private OnOffersClickListener onOffersClickListener;
    private OnBuyAddCartClickListener onBuyAddCartClickListener;
    public OffersAdapter(Class<LatestOffers> modelClass, int modelLayout, Class<OffersViewHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    public void setOnOffersClickListener(OnOffersClickListener onOffersClickListener) {
        this.onOffersClickListener = onOffersClickListener;
    }

    public void setOnBuyAddCartClickListener(OnBuyAddCartClickListener onBuyAddCartClickListener) {
        this.onBuyAddCartClickListener = onBuyAddCartClickListener;
    }

    @Override
    protected void populateViewHolder(OffersViewHolder viewHolder,final LatestOffers model, int position) {
        viewHolder.bindData(model,context);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onOffersClickListener)
                {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.offersBundleId,model);

                    onOffersClickListener.onOffersItemClicked(bundle);
                }
            }
        });
        viewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onBuyAddCartClickListener) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.addToCartBundleId, model);

                    onBuyAddCartClickListener.onAddToCartClicked(bundle);
                }

            }
        });
        viewHolder.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onBuyAddCartClickListener)
                {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constants.buyNowBundleId,model);

                    onBuyAddCartClickListener.onBuyNowClicked(bundle);
                }

            }
        });
    }
}
