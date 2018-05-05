package in.moneytransfer.ipay.home.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.activity.HomeActivity;
import in.moneytransfer.ipay.home.model.Passbook;
import in.moneytransfer.ipay.home.utils.Constants;
import in.moneytransfer.ipay.launch.manager.SharedPrefsManager;

public class PassbookService extends Service implements ValueEventListener {

    private DatabaseReference database;
    private FirebaseUser user;
    private SharedPrefsManager manager;
    public PassbookService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.PASSBOOK_DATABASE_REFERENCE)
                .child(user.getUid()).child("passbook");
        manager = new SharedPrefsManager(getBaseContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        database.addValueEventListener(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(),this.getClass());
        restartService.setPackage(getPackageName());
        startService(restartService);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists())
        {
            int count = 1;
            int passbookCount = manager.getPassbookCount(Constants.PASSBOOK_COUNT);
            int dataSnapCount = (int) dataSnapshot.getChildrenCount();
            for (DataSnapshot getData:dataSnapshot.getChildren())
            {
                if (dataSnapCount > passbookCount)
                {
                    if (count == dataSnapCount) {
                        showNotification(getData.getValue(Passbook.class));
                        manager.addPassbookCount(Constants.PASSBOOK_COUNT,dataSnapCount);
                        count++;
                        return;
                    }
                    else {
                        Passbook p = getData.getValue(Passbook.class);
                        ++count;
                    }
                }
            }
        }
    }
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
    private void showNotification(Passbook data)
    {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentInfo("Rs. "+data.getTransAmount())
                .setContentText(data.getTransType())
                .setSmallIcon(R.mipmap.ic_launcher);

        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
        int randomInt = new Random().nextInt(9999-1)+1;
        manager.notify(randomInt,builder.build());

    }
}
