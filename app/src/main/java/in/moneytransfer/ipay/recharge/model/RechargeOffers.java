package in.moneytransfer.ipay.recharge.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 24/09/17.
 */

public class RechargeOffers implements Parcelable {

    private String rechargePrice,rechargeValidity,rechargeDesc;

    public RechargeOffers() {
    }

    public String getRechargePrice() {
        return rechargePrice;
    }

    public void setRechargePrice(String rechargePrice) {
        this.rechargePrice = rechargePrice;
    }

    public String getRechargeValidity() {
        return rechargeValidity;
    }

    public void setRechargeValidity(String rechargeValidity) {
        this.rechargeValidity = rechargeValidity;
    }

    public String getRechargeDesc() {
        return rechargeDesc;
    }

    public void setRechargeDesc(String rechargeDesc) {
        this.rechargeDesc = rechargeDesc;
    }

    protected RechargeOffers(Parcel in) {
        rechargePrice = in.readString();
        rechargeValidity = in.readString();
        rechargeDesc = in.readString();
    }

    public static final Creator<RechargeOffers> CREATOR = new Creator<RechargeOffers>() {
        @Override
        public RechargeOffers createFromParcel(Parcel in) {
            return new RechargeOffers(in);
        }

        @Override
        public RechargeOffers[] newArray(int size) {
            return new RechargeOffers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rechargePrice);
        parcel.writeString(rechargeValidity);
        parcel.writeString(rechargeDesc);
    }
}
