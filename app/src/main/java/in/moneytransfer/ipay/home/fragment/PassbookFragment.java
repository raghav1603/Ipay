package in.moneytransfer.ipay.home.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.adapter.PassbookAdapter;
import in.moneytransfer.ipay.home.adapter.PassbookViewHolder;
import in.moneytransfer.ipay.home.model.Passbook;
import in.moneytransfer.ipay.home.utils.Constants;


public class PassbookFragment extends Fragment {

    private RecyclerView passbookRecycler;
    private PassbookAdapter adapter;
    private FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passbook, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        passbookRecycler = view.findViewById(R.id.passbook_reycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        passbookRecycler.setLayoutManager(manager);

        user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(Constants.PASSBOOK_DATABASE_REFERENCE)
                .child(user.getUid()).child("passbook");
        databaseReference.keepSynced(true);

        adapter = new PassbookAdapter(Passbook.class,R.layout.passbook_row_layout,
                PassbookViewHolder.class,databaseReference);

        passbookRecycler.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Passbook");
    }
}
