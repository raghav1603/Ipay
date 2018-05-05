package in.moneytransfer.ipay.profile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.LatestOffers;

/**
 * Created by raghav on 14/09/17.
 */

public class CartViewHolder extends RecyclerView.ViewHolder {

    private TextView itemName,itemPrice;
    private ImageView cartImage;
    ImageView deleteItem;
    View view;
    public CartViewHolder(View itemView) {
        super(itemView);

        view = itemView;
        itemName = itemView.findViewById(R.id.cart_row_itemname);
        itemPrice = itemView.findViewById(R.id.cart_row_itemprice);
        cartImage = itemView.findViewById(R.id.cart_row_imageview);
        deleteItem = itemView.findViewById(R.id.cart_row_delete);
    }
    public void bindCartData(LatestOffers offers, Context context)
    {
        itemPrice.setText("Rs. "+offers.getItemPrice());
        itemName.setText(offers.getItemName());

        Picasso.with(context).load(offers.getItemImageUrl()).resize(112,112)
                .centerInside().error(R.mipmap.ic_launcher).into(cartImage);

    }
}
