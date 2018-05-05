package in.moneytransfer.ipay.buysell.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.fragment.BuySellCommentsFragment;
import in.moneytransfer.ipay.buysell.model.BuySell;
import in.moneytransfer.ipay.buysell.utils.BuySellConstants;

public class BuySellDetailsActivity extends AppCompatActivity {

    private ImageView itemImage;
    private TextView itemName,itemPrice;
    private BuySell buySell;
    private ImageView disableButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_details);

        itemImage = findViewById(R.id.cart_row_imageview);
        itemName = findViewById(R.id.cart_row_itemname);
        itemPrice = findViewById(R.id.cart_row_itemprice);
        disableButton = findViewById(R.id.cart_row_delete);
        disableButton.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        buySell = bundle.getParcelable(BuySellConstants.BUY_SELL_BUNDLE_ID);
        showData();
    }
    private void showData()
    {
        Picasso.with(this).load(buySell.getBuySellItemImageUrl()).error(R.mipmap.ic_launcher_round).into(itemImage);
        itemName.setText("Item description:"+"\n"+buySell.getBuySellItemName());
        itemPrice.setText("Rs. "+buySell.getBuySellItemPrice());

        Bundle b = new Bundle();
        b.putString("data",buySell.getItemRef());

        BuySellCommentsFragment fragment = new BuySellCommentsFragment();
        fragment.setArguments(b);

        getFragmentManager().beginTransaction().add(R.id.all_comments_container,fragment)
                .commit();

    }
}
