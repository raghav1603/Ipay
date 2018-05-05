package in.moneytransfer.ipay.bustickets.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 17/09/17.
 */

public class BusTickets implements Parcelable {

    private String busDepartureTime;
    private String busDestination;
    private String busNumberOfAvailableSeats;
    private String busRegisterationNumber;
    private String busSource;
    private String busType;
    private String busRef;
    private String busTicketCost;

    public BusTickets() {
    }

    public void setBusNumberOfAvailableSeats(String busNumberOfAvailableSeats) {
        this.busNumberOfAvailableSeats = busNumberOfAvailableSeats;
    }

    public String getBusDepartureTime() {
        return busDepartureTime;
    }

    public String getBusDestination() {
        return busDestination;
    }

    public String getBusNumberOfAvailableSeats() {
        return busNumberOfAvailableSeats;
    }

    public String getBusRegisterationNumber() {
        return busRegisterationNumber;
    }

    public String getBusSource() {
        return busSource;
    }

    public String getBusType() {
        return busType;
    }

    public String getBusRef() {
        return busRef;
    }

    public String getBusTicketCost() {
        return busTicketCost;
    }

    protected BusTickets(Parcel in) {
        busDepartureTime = in.readString();
        busDestination = in.readString();
        busNumberOfAvailableSeats = in.readString();
        busRegisterationNumber = in.readString();
        busSource = in.readString();
        busType = in.readString();
        busRef = in.readString();
        busTicketCost = in.readString();
    }

    public static final Creator<BusTickets> CREATOR = new Creator<BusTickets>() {
        @Override
        public BusTickets createFromParcel(Parcel in) {
            return new BusTickets(in);
        }

        @Override
        public BusTickets[] newArray(int size) {
            return new BusTickets[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(busDepartureTime);
        parcel.writeString(busDestination);
        parcel.writeString(busNumberOfAvailableSeats);
        parcel.writeString(busRegisterationNumber);
        parcel.writeString(busSource);
        parcel.writeString(busType);
        parcel.writeString(busRef);
        parcel.writeString(busTicketCost);
    }
}
