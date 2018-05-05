package in.moneytransfer.ipay.profile.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.profile.adapter.AddressRecyclerAdapter;
import in.moneytransfer.ipay.profile.adapter.AddressViewHolder;
import in.moneytransfer.ipay.profile.utils.AddressOnClickListener;
import in.moneytransfer.ipay.profile.utils.ProfileConstants;

public class AllAddressFragment extends Fragment implements AddressOnClickListener,View.OnClickListener {


    private RecyclerView allAddressRecycler;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private FloatingActionButton addAddressButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_address, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addAddressButton = view.findViewById(R.id.add_address_floating_button);
        addAddressButton.setOnClickListener(this);
        allAddressRecycler = view.findViewById(R.id.all_address_recycler);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        allAddressRecycler.setLayoutManager(manager);

        DividerItemDecoration item = new DividerItemDecoration(getActivity(),manager.getOrientation());
        allAddressRecycler.addItemDecoration(item);

        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(ProfileConstants.DATABASE_REFERENCE_USER).child(user.getUid());

        AddressRecyclerAdapter addressRecyclerAdapter = new AddressRecyclerAdapter
                (String.class,R.layout.row_address_layout, AddressViewHolder.class,databaseReference.child("deliveryAddress"));


        allAddressRecycler.setAdapter(addressRecyclerAdapter);

        addressRecyclerAdapter.setAddressOnClickListener(this);

    }


    @Override
    public void onAddressClicked(String address) {
        Toast.makeText(getActivity(), "Address Selected", Toast.LENGTH_SHORT).show();
        Constants.ADDRESS_VARIABLE = address;
    }

    @Override
    public void onClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View promptsView = inflater.inflate(R.layout.dialog_layout,null);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(promptsView);

        final EditText addressText = promptsView.findViewById(R.id.editTextDialogUserInput);

        alertDialog.setCancelable(true)
                .setPositiveButton("Add address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String address = addressText.getText().toString().trim();

                        if (address.length() == 0)
                        {
                            return;
                        }

                        DatabaseReference pushRef = databaseReference.child("deliveryAddress");
                        pushRef.push().setValue(address);

                        addressText.setText("");

                        Toast.makeText(getActivity(), "New Address Added", Toast.LENGTH_SHORT).show();

                    }
                });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}
