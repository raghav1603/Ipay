package in.moneytransfer.ipay;

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class Ipay extends Application {

    public static Ipay mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }

    public static Ipay getmApplication() {
        return mApplication;
    }

}
