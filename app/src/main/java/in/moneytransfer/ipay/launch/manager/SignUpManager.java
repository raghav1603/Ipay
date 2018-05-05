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

public class SignUpManager implements OnCompleteListener<AuthResult>,CreateUserProfileManager.OnUserProfileChangedListener {

    private FirebaseAuth firebaseAuth;

    private SignUpCompleteListener signUpCompleteListener;

    private String username;

    public SignUpManager(SignUpCompleteListener signUpCompleteListener) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.signUpCompleteListener = signUpCompleteListener;
    }
    public void signUpUser(String username,String email,String password)
    {
        this.username = username;
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this);
    }

    public interface SignUpCompleteListener{
        void onSignUpSuccess(FirebaseUser user);
        void onSignUpFailure();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        FirebaseUser user = task.getResult().getUser();
        CreateUserProfileManager createUserProfileManager = new CreateUserProfileManager(this);
        createUserProfileManager.createFirebaseUserProfile(user,username);
    }

    @Override
    public void onUserProfileChangedListener(String username) {
        signUpCompleteListener.onSignUpSuccess(firebaseAuth.getCurrentUser());
    }

    @Override
    public void onProfileChangeFailed() {
        signUpCompleteListener.onSignUpFailure();
    }
}
