package in.moneytransfer.ipay.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.Passbook;

/**
 * Created by raghav on 05/09/17.
 */

public class PassbookViewHolder extends RecyclerView.ViewHolder{

    private TextView transactionType,transactionDate,transactionAmount;
    public PassbookViewHolder(View itemView) {
        super(itemView);

        transactionAmount = itemView.findViewById(R.id.passbook_transaction_amount);
        transactionDate = itemView.findViewById(R.id.passbook_transaction_date);
        transactionType = itemView.findViewById(R.id.passbook_transaction_type);
    }
    public void bindPassbookData(Passbook data)
    {
        transactionAmount.setText("Rs. "+data.getTransAmount());
        transactionType.setText(data.getTransType());
        transactionDate.setText(data.getTransDate());


    }
}
