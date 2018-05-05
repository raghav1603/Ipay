package in.moneytransfer.ipay.launch.manager;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mayankchauhan on 05/09/17.
 */

public class SignInManager implements OnCompleteListener<AuthResult> {

    private FirebaseAuth firebaseAuth;
    private SignInCompleteListener signInCompleteListener;

    public interface SignInCompleteListener{

        void onSignInSuccess(FirebaseUser user);
        void onSignInFailure();
    }

    public SignInManager(SignInCompleteListener signInCompleteListener) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.signInCompleteListener = signInCompleteListener;
    }
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (null != user) {
            signInCompleteListener.onSignInSuccess(user);
        }
        else {
            signInCompleteListener.onSignInFailure();
        }
    }
    public void signInUser(String email,String password){
        firebaseAuth.signInWithEmailAndPassword(email, password);
    }
}
