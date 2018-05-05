package in.moneytransfer.ipay.buysell.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.model.BuySell;

/**
 * Created by raghav on 16/09/17.
 */

public class BuySellItemsViewHolder extends RecyclerView.ViewHolder{

    private ImageView buySellItemImage;
    private TextView buySellItemPrice,buySellItemName;
    View view;
    public BuySellItemsViewHolder(View itemView) {
        super(itemView);

        view = itemView;
        buySellItemImage = itemView.findViewById(R.id.buy_sell_row_imageview);
        buySellItemPrice = itemView.findViewById(R.id.buy_sell_row_textview);
        buySellItemName = itemView.findViewById(R.id.buy_sell_row_itemName);
    }
    public void bindBuySellItems(BuySell data, Context context)
    {
        Picasso.with(context).load(data.getBuySellItemImageUrl())
                .resize(200,300).centerInside().onlyScaleDown().error(R.mipmap.ic_launcher_round).into(buySellItemImage);
        buySellItemPrice.setText("Rs. "+ data.getBuySellItemPrice());
        buySellItemName.setText(data.getBuySellItemName());

    }
}
