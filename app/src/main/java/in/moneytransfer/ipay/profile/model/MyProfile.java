package in.moneytransfer.ipay.profile.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raghav on 10/09/17.
 */

public class MyProfile implements Parcelable {
    private String userEmailId;
    private String userPhoneNumber;
    private String userUid;
    private String userBalance;
    private String userDisplayName;

    public MyProfile() {
    }


    public MyProfile(String userEmailId, String userPhoneNumber, String userUid,
                     String userBalance,String userDisplayName) {
        this.userEmailId = userEmailId;
        this.userPhoneNumber = userPhoneNumber;
        this.userUid = userUid;
        this.userBalance = userBalance;
        this.userDisplayName = userDisplayName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(String userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    protected MyProfile(Parcel in) {
        userEmailId = in.readString();
        userPhoneNumber = in.readString();
        userUid = in.readString();
        userBalance = in.readString();
        userDisplayName = in.readString();
    }

    public static final Creator<MyProfile> CREATOR = new Creator<MyProfile>() {
        @Override
        public MyProfile createFromParcel(Parcel in) {
            return new MyProfile(in);
        }

        @Override
        public MyProfile[] newArray(int size) {
            return new MyProfile[size];
        }
    };

    public Map<String,Object> toMap()
    {
        HashMap<String,Object> result = new HashMap<>();

        result.put("userEmailId",userEmailId);
        result.put("userPhoneNumber",userPhoneNumber);
        result.put("userUid",userUid);
        result.put("userBalance",userBalance);
        result.put("userDisplayName",userDisplayName);

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userEmailId);
        parcel.writeString(userPhoneNumber);
        parcel.writeString(userUid);
        parcel.writeString(userBalance);
        parcel.writeString(userDisplayName);
    }
}
