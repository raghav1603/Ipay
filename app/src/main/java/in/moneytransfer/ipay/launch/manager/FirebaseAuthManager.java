package in.moneytransfer.ipay.launch.manager;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public class FirebaseAuthManager implements FirebaseAuth.AuthStateListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuthCompleteListener firebaseAuthCompleteListener;

    public FirebaseAuthManager(FirebaseAuthCompleteListener firebaseAuthCompleteListener) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseAuthCompleteListener = firebaseAuthCompleteListener;
    }
    public void checkUserAuth()
    {
        firebaseAuth.addAuthStateListener(this);
    }
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (null != user)
        {
            firebaseAuthCompleteListener.onAuthSuccess(user);
        }
        else {
            firebaseAuthCompleteListener.onAuthFailure();
        }

    }
}
