package in.moneytransfer.ipay.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 05/09/17.
 */

public class Passbook implements Parcelable {

    private String transType;
    private String transAmount;
    private String transDate;

    public Passbook() {
    }

    protected Passbook(Parcel in) {
        transType = in.readString();
        transAmount = in.readString();
        transDate = in.readString();
    }

    public static final Creator<Passbook> CREATOR = new Creator<Passbook>() {
        @Override
        public Passbook createFromParcel(Parcel in) {
            return new Passbook(in);
        }

        @Override
        public Passbook[] newArray(int size) {
            return new Passbook[size];
        }
    };

    public String getTransType() {
        return transType;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(transType);
        parcel.writeString(transAmount);
        parcel.writeString(transDate);
    }
}
