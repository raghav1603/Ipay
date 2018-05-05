package in.moneytransfer.ipay.home.adapter;

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
 * Created by raghav on 19/08/17.
 */

public class OffersViewHolder extends RecyclerView.ViewHolder {

    private ImageView offersImage;
    private TextView offersName;
    private TextView offersPrice;
    Button buyNow,addToCart;
    View view;
    public OffersViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        offersImage =  itemView.findViewById(R.id.offers_row_imageview);
        offersName = itemView.findViewById(R.id.offers_row_itemname);
        offersPrice = itemView.findViewById(R.id.offers_row_itemprice);
        buyNow = itemView.findViewById(R.id.buy_now);
        addToCart = itemView.findViewById(R.id.add_cart);
    }
    public void bindData(LatestOffers offers, Context context)
    {
        Picasso.with(context).load(offers.getItemImageUrl())
                .resize(500,700)
                .centerInside()
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher).into(offersImage);
        offersName.setText(offers.getItemName());
        offersPrice.setText("Rs. "+offers.getItemPrice());

    }
}
