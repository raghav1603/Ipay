package in.moneytransfer.ipay.home.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by raghav on 13/08/17.
 */

public class UserOptions implements Parcelable {
    private String optionName;
    private String optionImg;

    public UserOptions() {
    }

    public UserOptions(String optionName, String optionImg) {
        this.optionName = optionName;
        this.optionImg = optionImg;
    }

    protected UserOptions(Parcel in) {
        optionName = in.readString();
        optionImg = in.readString();
    }

    public static final Creator<UserOptions> CREATOR = new Creator<UserOptions>() {
        @Override
        public UserOptions createFromParcel(Parcel in) {
            return new UserOptions(in);
        }

        @Override
        public UserOptions[] newArray(int size) {
            return new UserOptions[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(optionName);
        parcel.writeString(optionImg);
    }

    public String getOptionImg() {
        return optionImg;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public void setOptionImg(String optionImg) {
        this.optionImg = optionImg;
    }
}
