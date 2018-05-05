package in.moneytransfer.ipay.bustickets.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.bustickets.model.BusTickets;

/**
 * Created by raghav on 21/09/17.
 */

public class BusTicketsViewHolder extends RecyclerView.ViewHolder {

    private TextView source,destination,departureTime,busType;
    View view;
    public BusTicketsViewHolder(View itemView) {
        super(itemView);

        view = itemView;

        source = itemView.findViewById(R.id.bus_row_source);
        destination = itemView.findViewById(R.id.bus_row_destination);
        departureTime = itemView.findViewById(R.id.bus_row_departure);
        busType = itemView.findViewById(R.id.bus_row_type);
    }
    public void bindBusTicketsData(BusTickets tickets)
    {
        source.setText(tickets.getBusSource());
        destination.setText(tickets.getBusDestination());
        departureTime.setText(tickets.getBusDepartureTime());
        busType.setText(tickets.getBusType());
    }
}
