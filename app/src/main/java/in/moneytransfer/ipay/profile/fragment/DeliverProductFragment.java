package in.moneytransfer.ipay.profile.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.model.MyProfile;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

public class DeliverProductFragment extends Fragment implements View.OnClickListener,AccountBalanceListener {

    private LatestOffers offers;
    private ImageView itemImage;
    private TextView itemName,itemPrice;
    private Button buyNowButton;
    private Activity activity;
    private ImageView deleteImage;
    private TransactionManager manager;
    private Double remBalance,itemCost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromBundle();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_deliver_product, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemImage = view.findViewById(R.id.cart_row_imageview);
        itemName = view.findViewById(R.id.cart_row_itemname);
        itemPrice = view.findViewById(R.id.cart_row_itemprice);
        buyNowButton = view.findViewById(R.id.deliver_fragment_buynow);
        deleteImage = view.findViewById(R.id.cart_row_delete);
        deleteImage.setVisibility(View.GONE);
        buyNowButton.setOnClickListener(this);

        showData();

        activity = getActivity();

        manager = new TransactionManager(this);

        loadAllAddresses();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.setTitle("Check Out");
    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        offers = bundle.getParcelable(Constants.buyNowBundleId);
    }
    private void loadAllAddresses()
    {
        activity.getFragmentManager()
                .beginTransaction()
                .add(R.id.delivery_product_container,new AllAddressFragment())
                .commit();
    }
    private void showData()
    {
        Picasso.with(activity).load(offers.getItemImageUrl()).error(R.mipmap.ic_launcher).into(itemImage);
        itemPrice.setText("Rs. "+offers.getItemPrice());
        itemName.setText(offers.getItemName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.deliver_fragment_buynow:
            {
                itemCost = Double.valueOf(offers.getItemPrice());
                if (Constants.ADDRESS_VARIABLE.equals("noAddress"))
                {
                    Toast.makeText(activity, "Select Address or Add New", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (remBalance > itemCost)
                {
                    manager.updateAccountBalance(remBalance,itemCost,"Purchased a Product");

                    Toast.makeText(activity, "Item bought", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            activity.finish();
                        }
                    },1000);
                    break;
                }
                else {
                    Toast.makeText(activity, "Insufficient balance", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onAccountBalanceRetrieved(double balance) {
        remBalance = balance;
    }
}
