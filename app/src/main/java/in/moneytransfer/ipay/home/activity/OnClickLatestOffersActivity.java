package in.moneytransfer.ipay.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;

public class OnClickLatestOffersActivity extends AppCompatActivity {

    private TextView price,description;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_latest_offers);


        initializeUI();
        setData();

    }
    private void initializeUI()
    {
        price = findViewById(R.id.onclick_offers_price);
        description =  findViewById(R.id.onclick_offers_description);
        imageView =  findViewById(R.id.onclick_offers_imageview);
    }
    private void setData()
    {
        Bundle bundle = getIntent().getExtras();
        LatestOffers offer = bundle.getParcelable(Constants.offersBundleId);

        price.setText("Rs. "+offer.getItemPrice());
        description.setText(offer.getItemDescription());
        Picasso.with(this).load(offer.getItemImageUrl()).resize(600,800).centerInside().into(imageView);
    }
}
