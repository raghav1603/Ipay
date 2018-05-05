package in.moneytransfer.ipay.launch.manager;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public class CreateUserProfileManager implements OnCompleteListener<Void> {

    private FirebaseUser user;

    private OnUserProfileChangedListener onUserProfileChangedListener;

    public CreateUserProfileManager(OnUserProfileChangedListener onUserProfileChangedListener) {
        this.onUserProfileChangedListener = onUserProfileChangedListener;
    }
    public interface OnUserProfileChangedListener{
        void onUserProfileChangedListener(String username);
        void onProfileChangeFailed();
    }

    public void createFirebaseUserProfile(FirebaseUser user,String username)
    {
        this.user = user;

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(addProfileName).addOnCompleteListener(this);
    }
    @Override
    public void onComplete(@NonNull Task<Void> task) {

        if (! task.isSuccessful()){
            onUserProfileChangedListener.onUserProfileChangedListener(user.getDisplayName());
        }
        else{
            onUserProfileChangedListener.onProfileChangeFailed();
        }
    }
}
