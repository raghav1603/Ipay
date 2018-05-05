package in.moneytransfer.ipay.home.manager;

import java.util.ArrayList;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.UserOptions;

/**
 * Created by raghav on 13/08/17.
 */

public class UserOptionsData {

    private ArrayList<UserOptions> list = new ArrayList<>();

    public UserOptionsData() {
    }

    public  ArrayList<UserOptions> getUserData()
    {
        list.clear();

        list.add(new UserOptions("Pay","https://firebasestorage.googleapis.com/v0/b/ipay-385ad.appspot.com/o/userOptions%2FPay.png?alt=media&token=6cf52d3c-5f20-4ece-b08c-3f6635da2ef8"));
        list.add(new UserOptions("Add Money","https://firebasestorage.googleapis.com/v0/b/ipay-385ad.appspot.com/o/userOptions%2FAdd%20Money.png?alt=media&token=96130de2-f04a-4d45-9e3e-c2bfd5e901a7"));
        list.add(new UserOptions("Passbook","https://firebasestorage.googleapis.com/v0/b/ipay-385ad.appspot.com/o/userOptions%2FPassbook.png?alt=media&token=045d4f47-1e76-4c32-8046-a7738490f7e4"));
        list.add(new UserOptions("Accept","https://firebasestorage.googleapis.com/v0/b/ipay-385ad.appspot.com/o/userOptions%2FAccept%20Payment.png?alt=media&token=a7cd5e8e-3b17-4a2d-b2df-660bd3aa7ca3"));
        list.add(new UserOptions("Nearby","https://firebasestorage.googleapis.com/v0/b/ipay-385ad.appspot.com/o/userOptions%2FNearby.png?alt=media&token=54153095-9faf-4bf0-985e-1648d96df7fb"));

        return list;
    }
}
