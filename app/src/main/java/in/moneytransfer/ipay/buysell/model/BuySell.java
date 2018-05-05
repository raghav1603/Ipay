package in.moneytransfer.ipay.buysell.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 16/09/17.
 */

public class BuySell implements Parcelable {
    private String buySellItemName;
    private String buySellItemPrice;
    private String buySellItemImageUrl;
    private String buySellItemDescription;
    private String itemRef;

    public BuySell() {
    }

    public BuySell(String buySellItemName, String buySellItemPrice, String buySellItemImageUrl,
                   String buySellItemDescription, String itemRef) {
        this.buySellItemName = buySellItemName;
        this.buySellItemPrice = buySellItemPrice;
        this.buySellItemImageUrl = buySellItemImageUrl;
        this.buySellItemDescription = buySellItemDescription;
        this.itemRef = itemRef;
    }

    public String getBuySellItemName() {
        return buySellItemName;
    }

    public void setBuySellItemName(String buySellItemName) {
        this.buySellItemName = buySellItemName;
    }

    public String getBuySellItemPrice() {
        return buySellItemPrice;
    }

    public void setBuySellItemPrice(String buySellItemPrice) {
        this.buySellItemPrice = buySellItemPrice;
    }

    public String getBuySellItemImageUrl() {
        return buySellItemImageUrl;
    }

    public void setBuySellItemImageUrl(String buySellItemImageUrl) {
        this.buySellItemImageUrl = buySellItemImageUrl;
    }

    public String getBuySellItemDescription() {
        return buySellItemDescription;
    }

    public void setBuySellItemDescription(String buySellItemDescription) {
        this.buySellItemDescription = buySellItemDescription;
    }

    public String getItemRef() {
        return itemRef;
    }

    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }

    protected BuySell(Parcel in) {
        buySellItemName = in.readString();
        buySellItemPrice = in.readString();
        buySellItemImageUrl = in.readString();
        buySellItemDescription = in.readString();
        itemRef = in.readString();
    }

    public static final Creator<BuySell> CREATOR = new Creator<BuySell>() {
        @Override
        public BuySell createFromParcel(Parcel in) {
            return new BuySell(in);
        }

        @Override
        public BuySell[] newArray(int size) {
            return new BuySell[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(buySellItemName);
        parcel.writeString(buySellItemPrice);
        parcel.writeString(buySellItemImageUrl);
        parcel.writeString(buySellItemDescription);
        parcel.writeString(itemRef);
    }
}
