package in.moneytransfer.ipay.launch.manager;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public interface FirebaseAuthCompleteListener {

    void onAuthSuccess(FirebaseUser user);
    void onAuthFailure();
}
