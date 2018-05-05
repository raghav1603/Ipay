package in.moneytransfer.ipay.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.UserOptions;
import in.moneytransfer.ipay.home.utils.OnRecyclerViewItemClickListener;

/**
 * Created by raghav on 13/08/17.
 */

public class UserOptionsAdapter extends RecyclerView.Adapter<UserOptionsViewHolder> {

    private ArrayList<UserOptions> list = new ArrayList<>();
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private Context context;
    private LayoutInflater inflater;

    public UserOptionsAdapter(ArrayList<UserOptions> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public UserOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_options_row_layout,parent,false);
        return new UserOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserOptionsViewHolder holder, final int position) {

        holder.bindData(list.get(position),context);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onRecyclerViewItemClickListener)
                {
                    onRecyclerViewItemClickListener.onItemClicked(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
