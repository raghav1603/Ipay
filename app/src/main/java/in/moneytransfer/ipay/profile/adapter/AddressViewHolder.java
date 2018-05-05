package in.moneytransfer.ipay.profile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.moneytransfer.ipay.R;

/**
 * Created by raghav on 15/09/17.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder {
    TextView address;
    View view;
    public AddressViewHolder(View itemView) {
        super(itemView);

        view = itemView;
        address = itemView.findViewById(R.id.row_address_textview);
    }
}
