package in.moneytransfer.ipay.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 19/08/17.
 */

public class LatestOffers implements Parcelable {

    private String itemImageUrl;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemRef;

    public LatestOffers() {
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemRef() {
        return itemRef;
    }



    public void setItemRef(String itemRef) {
        this.itemRef = itemRef;
    }

    protected LatestOffers(Parcel in) {
        itemImageUrl = in.readString();
        itemName = in.readString();
        itemDescription = in.readString();
        itemPrice = in.readString();
        itemRef = in.readString();
    }

    public static final Creator<LatestOffers> CREATOR = new Creator<LatestOffers>() {
        @Override
        public LatestOffers createFromParcel(Parcel in) {
            return new LatestOffers(in);
        }

        @Override
        public LatestOffers[] newArray(int size) {
            return new LatestOffers[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemImageUrl);
        parcel.writeString(itemName);
        parcel.writeString(itemDescription);
        parcel.writeString(itemPrice);
        parcel.writeString(itemRef);
    }
}
