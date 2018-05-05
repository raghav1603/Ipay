package in.moneytransfer.ipay.bustickets.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.bustickets.model.BusTickets;
import in.moneytransfer.ipay.bustickets.utils.BusConstants;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;

/**
 * Created by raghav on 21/09/17.
 */

public class BusTicketsRecyclerAdapter extends RecyclerView.Adapter<BusTicketsViewHolder> {

    private ArrayList<BusTickets> ticketsArrayList;
    private LayoutInflater inflater;
    private OnOffersClickListener clickListener;

    public BusTicketsRecyclerAdapter(Context context,ArrayList<BusTickets> list) {
        inflater = LayoutInflater.from(context);
        ticketsArrayList = list;
    }

    public void setClickListener(OnOffersClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public BusTicketsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.bus_row_layout,parent,false);
        return new BusTicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusTicketsViewHolder holder, int position) {

        final BusTickets busTickets = ticketsArrayList.get(position);
        holder.bindBusTicketsData(busTickets);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null !=  clickListener)
                {
                    Bundle bundle = new Bundle();

                    bundle.putParcelable(BusConstants.BUS_DATA_CONSTANTS,busTickets);
                    clickListener.onOffersItemClicked(bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketsArrayList.size();
    }
}
