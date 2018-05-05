package in.moneytransfer.ipay.profile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.activity.OnClickLatestOffersActivity;
import in.moneytransfer.ipay.home.model.LatestOffers;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;
import in.moneytransfer.ipay.profile.adapter.CartRecyclerAdapter;
import in.moneytransfer.ipay.profile.adapter.CartViewHolder;
import in.moneytransfer.ipay.profile.utils.DeleteCartItemListener;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class MyCartActivity extends AppCompatActivity implements OnOffersClickListener,DeleteCartItemListener {

    private RecyclerView cartRecycler;
    private ProgressBar cartProgressBar;
    private String root_reference = "https://ipay-385ad.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        cartProgressBar = findViewById(R.id.cart_progress_bar);
        cartProgressBar.setVisibility(View.GONE);

        cartRecycler = findViewById(R.id.cart_recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        cartRecycler.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,manager.getOrientation());
        cartRecycler.addItemDecoration(itemDecoration);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        root_reference = root_reference+"users/"+user.getUid()+"/cart";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(root_reference);
        reference.keepSynced(true);
        CartRecyclerAdapter adapter = new CartRecyclerAdapter(LatestOffers.class,R.layout.cart_row_layout, CartViewHolder.class,reference,this);

        cartRecycler.setAdapter(adapter);
        adapter.setOnOffersClickListener(this);
        adapter.setDeleteCartItemListener(this);

    }

    @Override
    public void onOffersItemClicked(Bundle bundle) {
        Intent intent = new Intent(this, MyDeliveryActivity.class);
        intent.putExtras(bundle);
        intent.putExtra(ProfileConstants.FRAGMENT_CHOICE,2);
        startActivity(intent);
    }

    @Override
    public void deleteItemRef(String itemRef) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl(root_reference);
        ref.child(itemRef).removeValue();
    }
}
