package in.moneytransfer.ipay.home.adapter;

import android.support.annotation.LayoutRes;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.home.model.Passbook;

/**
 * Created by raghav on 05/09/17.
 */

public class PassbookAdapter extends FirebaseRecyclerAdapter<Passbook,PassbookViewHolder> {


    public PassbookAdapter(Class<Passbook> modelClass, @LayoutRes int modelLayout, Class<PassbookViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    protected void populateViewHolder(PassbookViewHolder viewHolder, Passbook model, int position) {
        viewHolder.bindPassbookData(model);
    }
}
