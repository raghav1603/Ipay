package in.moneytransfer.ipay.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.activity.OnClickOptionsActivity;
import in.moneytransfer.ipay.home.adapter.UserOptionsAdapter;
import in.moneytransfer.ipay.home.manager.UserOptionsData;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.home.utils.OnBuyAddCartClickListener;
import in.moneytransfer.ipay.home.utils.OnRecyclerViewItemClickListener;

public class UserOptionsFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private RecyclerView optionsRecycler;
    private UserOptionsAdapter adapter;
    private UserOptionsData data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_options, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        optionsRecycler = view.findViewById(R.id.userOptionsRecycler);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        optionsRecycler.setLayoutManager(manager);
        optionsRecycler.setHasFixedSize(true);

        data = new UserOptionsData();

        adapter = new UserOptionsAdapter(data.getUserData(),getActivity());
        optionsRecycler.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(this);

    }

    @Override
    public void onItemClicked(final int position) {

        Intent intent = new Intent(getActivity(), OnClickOptionsActivity.class);
        intent.putExtra(Constants.optionsFragToAct,position);
        startActivity(intent);
    }

}
