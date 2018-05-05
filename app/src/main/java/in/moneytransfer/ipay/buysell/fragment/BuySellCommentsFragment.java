package in.moneytransfer.ipay.buysell.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.profile.adapter.AddressRecyclerAdapter;
import in.moneytransfer.ipay.profile.adapter.AddressViewHolder;
import in.moneytransfer.ipay.profile.utils.AddressOnClickListener;

public class BuySellCommentsFragment extends Fragment implements View.OnClickListener,AddressOnClickListener {

    private RecyclerView allCommentsRecycler;
    private EditText commentText;
    private Button commentButton;
    private DatabaseReference databaseReference;
    private String ref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        ref = bundle.getString("data");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_sell_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allCommentsRecycler = view.findViewById(R.id.all_comments_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        allCommentsRecycler.setLayoutManager(manager);

        commentText = view.findViewById(R.id.all_comments_editText);
        commentButton = view.findViewById(R.id.comment_button);
        commentButton.setOnClickListener(this);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),manager.getOrientation());
        allCommentsRecycler.addItemDecoration(itemDecoration);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("buySell").child(ref);

        AddressRecyclerAdapter adapter = new AddressRecyclerAdapter
                (String.class,R.layout.row_address_layout, AddressViewHolder.class,databaseReference.child("comments"));

        allCommentsRecycler.setAdapter(adapter);
        adapter.setAddressOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        uploadComment();
    }
    private void uploadComment()
    {
        String com = commentText.getText().toString().trim();
        DatabaseReference mRef = databaseReference.child("comments");
        mRef.push().setValue(com);

        commentText.setText("");
        Toast.makeText(getActivity(), "Comment added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddressClicked(String address) {
        Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
    }
}
