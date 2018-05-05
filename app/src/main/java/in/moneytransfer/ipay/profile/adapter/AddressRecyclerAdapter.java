package in.moneytransfer.ipay.profile.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.profile.utils.AddressOnClickListener;

/**
 * Created by raghav on 15/09/17.
 */

public class AddressRecyclerAdapter extends FirebaseRecyclerAdapter<String,AddressViewHolder> {

    private AddressOnClickListener addressOnClickListener;
    public AddressRecyclerAdapter(Class<String> modelClass, @LayoutRes int modelLayout, Class<AddressViewHolder> viewHolderClass, Query query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    public void setAddressOnClickListener(AddressOnClickListener addressOnClickListener) {
        this.addressOnClickListener = addressOnClickListener;
    }

    @Override
    protected void populateViewHolder(final AddressViewHolder viewHolder, final String model, int position) {
        viewHolder.address.setText(model);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.view.setBackgroundColor(Color.parseColor("#4A4A4A"));
                viewHolder.address.setTextColor(Color.parseColor("#FFFFFF"));
                addressOnClickListener.onAddressClicked(model);
            }
        });
    }
}
