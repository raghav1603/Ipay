package in.moneytransfer.ipay.buysell.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.buysell.adapter.BuySellItemsAdapter;
import in.moneytransfer.ipay.buysell.adapter.BuySellItemsViewHolder;
import in.moneytransfer.ipay.buysell.manager.BuySellDataRetriever;
import in.moneytransfer.ipay.buysell.manager.BuySellFragmentManager;
import in.moneytransfer.ipay.buysell.model.BuySell;
import in.moneytransfer.ipay.buysell.utils.BuySellConstants;
import in.moneytransfer.ipay.home.utils.DataProgressListener;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;

public class BuySellItemsFragment extends Fragment implements View.OnClickListener,OnOffersClickListener,DataProgressListener {

    private RecyclerView buySellRecycler;
    private ProgressBar buySellProgressBar;
    private FloatingActionButton buySellAddButton;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_sell_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buySellRecycler = view.findViewById(R.id.buy_sell_recycler);
        buySellProgressBar = view.findViewById(R.id.buy_sell_progressbar);
        buySellAddButton = view.findViewById(R.id.buy_sell_addButton);
        buySellAddButton.setOnClickListener(this);
        activity = getActivity();

        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        buySellRecycler.setLayoutManager(manager);

        BuySellDataRetriever retriever = new BuySellDataRetriever(this,activity);
        BuySellItemsAdapter adapter = retriever.getAdapter();

        buySellRecycler.setAdapter(adapter);
        adapter.setOnOffersClickListener(this);
    }

    @Override
    public void onClick(View view) {
        BuySellFragmentManager manager = new BuySellFragmentManager(activity);
        manager.loadAddBuySellItemFragment();
    }

    @Override
    public void onOffersItemClicked(Bundle bundle) {
        BuySellFragmentManager manager = new BuySellFragmentManager(activity);
        manager.loadBuySellDetailsFragment(bundle);
    }

    @Override
    public void onFetchStarted() {
        buySellProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchCompleted() {
        buySellProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFetchCancelled() {
        buySellProgressBar.setVisibility(View.GONE);
    }
}
