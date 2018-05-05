package in.moneytransfer.ipay.bustickets.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.bustickets.adapter.BusTicketsRecyclerAdapter;
import in.moneytransfer.ipay.bustickets.manager.BusDataRetriever;
import in.moneytransfer.ipay.bustickets.model.BusTickets;
import in.moneytransfer.ipay.bustickets.utils.BusConstants;
import in.moneytransfer.ipay.bustickets.utils.BusDataListener;
import in.moneytransfer.ipay.home.utils.OnOffersClickListener;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

public class BusTicketsActivity extends AppCompatActivity implements
        View.OnClickListener,BusDataListener,OnOffersClickListener {

    private RecyclerView busRecycler;
    private ProgressBar progressBar;
    private Button filterButton;
    private BusTicketsRecyclerAdapter adapter;
    private ArrayList<BusTickets> busTicketArrayList;
    private Spinner sourceSpinner;
    private Spinner destinationSpinner;
    private ArrayAdapter<CharSequence> charSequenceArrayAdapter;
    private String busSrc,busDest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_tickets);

        progressBar = findViewById(R.id.bus_progress_bar);
        progressBar.setVisibility(View.GONE);

        sourceSpinner = findViewById(R.id.bus_source);
        destinationSpinner = findViewById(R.id.bus_destination);

        charSequenceArrayAdapter = ArrayAdapter.createFromResource(this,R.array.cities_list,android.R.layout.simple_spinner_item);
        charSequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(charSequenceArrayAdapter);
        destinationSpinner.setAdapter(charSequenceArrayAdapter);

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                busSrc = (String) adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                busSrc = (String) adapterView.getItemAtPosition(0);
            }
        });

        destinationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                busDest = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                busDest = (String) adapterView.getItemAtPosition(0);
            }
        });

        filterButton = findViewById(R.id.bus_filter_button);
        filterButton.setOnClickListener(this);

        busRecycler = findViewById(R.id.bus_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        busRecycler.setLayoutManager(manager);

    }
    @Override
    public void onClick(View view) {

        if (busSrc.length() == 0)
        {
            Toast.makeText(this, "Enter Source", Toast.LENGTH_SHORT).show();
            return;
        }

        if (busDest.length() == 0)
        {
            Toast.makeText(this, "Enter Destination", Toast.LENGTH_SHORT).show();
            return;
        }
        new BusDataRetriever(this,busSrc);

    }

    @Override
    public void onBusDataFetchStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBusDataFetchCompleted(ArrayList<BusTickets> ticketsArrayList) {
        filterData(ticketsArrayList,busDest);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBusDataCancelled() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onOffersItemClicked(Bundle bundle) {
        Intent intent = new Intent(this,BookBusActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void filterData(ArrayList<BusTickets> busTicketList,String destinationName)
    {
        busTicketArrayList = new ArrayList<>();

        for (BusTickets ticket:busTicketList)
        {
            if (ticket.getBusDestination().equals(destinationName))
            {
                busTicketArrayList.add(ticket);
            }
        }

        adapter = new BusTicketsRecyclerAdapter(this,busTicketArrayList);
        busRecycler.setAdapter(adapter);
        adapter.setClickListener(this);

        if (busTicketArrayList.size() == 0)
        {
            Toast.makeText(this, "No Bus Available", Toast.LENGTH_SHORT).show();
        }
    }

}
