package in.moneytransfer.ipay.bustickets.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.bustickets.model.BusTickets;
import in.moneytransfer.ipay.bustickets.utils.BusConstants;
import in.moneytransfer.ipay.home.activity.HomeActivity;
import in.moneytransfer.ipay.profile.manager.TransactionManager;
import in.moneytransfer.ipay.profile.utils.AccountBalanceListener;

public class BookBusActivity extends AppCompatActivity implements
        AccountBalanceListener,View.OnClickListener {

    private TextView bookSource,bookDestination,bookDeparture,bookSeatsLeft
            ,bookBusNumber,bookBusType,bookBusCost;
    private EditText ticketNumber;
    private Button bookTickets;
    private BusTickets tickets;
    private Double accountBalance = 0.0;
    private Double ticketsLeft;
    private Double ticketCost;
    private TransactionManager manager;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);

        initializeUi();

        Bundle bundle = getIntent().getExtras();
        tickets = bundle.getParcelable(BusConstants.BUS_DATA_CONSTANTS);
        showData(tickets);

        ref = FirebaseDatabase.getInstance().getReference()
                .child("busTickets").child(tickets.getBusRef());

        manager = new TransactionManager(this);
    }

    private void showData(BusTickets ticket) {
        bookSource.setText("Source  "+ticket.getBusSource());
        bookDestination.setText("Destination  "+ticket.getBusDestination());

        bookBusCost.setText("Rs."+ticket.getBusTicketCost());
        ticketCost = Double.valueOf(ticket.getBusTicketCost());

        bookDeparture.setText("Departure Time  "+ticket.getBusDepartureTime());

        bookSeatsLeft.setText("Seats Left  "+ticket.getBusNumberOfAvailableSeats());
        ticketsLeft = Double.valueOf(ticket.getBusNumberOfAvailableSeats());

        bookBusNumber.setText("Registeration Number  "+ticket.getBusRegisterationNumber());
        bookBusType.setText("Bus Type  "+ ticket.getBusType());
    }

    private void initializeUi()
    {
        bookSource = findViewById(R.id.book_source);
        bookDestination = findViewById(R.id.book_destination);
        bookDeparture = findViewById(R.id.book_time);
        bookSeatsLeft = findViewById(R.id.book_seats_left);
        bookBusNumber = findViewById(R.id.book_bus_number);
        bookBusType = findViewById(R.id.book_bus_type);
        bookBusCost = findViewById(R.id.book_bus_cost);
        ticketNumber = findViewById(R.id.book_number_tickets);
        bookTickets = findViewById(R.id.book_bus_tickets);
        bookTickets.setOnClickListener(this);

    }

    @Override
    public void onAccountBalanceRetrieved(double balance) {
        accountBalance = balance;
    }

    @Override
    public void onClick(View view) {
        double numberOfTickets = Double.parseDouble((ticketNumber.getText().toString().trim()));
        if (numberOfTickets <= 0)
        {
            Toast.makeText(this, "Select number of tickets", Toast.LENGTH_SHORT).show();
            return;
        }
        if (numberOfTickets > ticketsLeft)
        {
            Toast.makeText(this, "Only "+ticketsLeft.intValue()+" seats left", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((numberOfTickets * ticketCost > accountBalance))
        {
            Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_SHORT).show();
            return;
        }
        manager.updateAccountBalance(accountBalance,(numberOfTickets*ticketCost),"Debited for Bus Ticket");

        final int seatsLeft = (int) (ticketsLeft.intValue() - numberOfTickets);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DatabaseReference mRef = ref;
                mRef.child("busNumberOfAvailableSeats").setValue(seatsLeft+"");

                Intent intent = new Intent(BookBusActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intent);
            }
        },100);

        Toast.makeText(this, "Bus Booked", Toast.LENGTH_SHORT).show();
    }
}
