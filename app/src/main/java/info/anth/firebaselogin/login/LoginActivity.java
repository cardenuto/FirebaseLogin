//TODO: (Required) Add your package name
package info.anth.firebaselogin.login;

//TODO: (Required) Add your package name to the resource import
import info.anth.firebaselogin.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.auth.api.Auth;

public class LoginActivity extends FirebaseLoginBaseActivity {

    public static final int RESULT_REQUEST_CODE = 1;

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();
    public static final Boolean LOG_SHOW = false;

    private Firebase mRef;
    private LoginRegisterDialog loginRegisterDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
        context = this;

        // set login button listener
        Button buttonLogin = (Button) findViewById(R.id.activity_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickLogin();
            }
        });

        // set cancel button listener
        Button buttonCancel = (Button) findViewById(R.id.activity_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickCancel();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        logout();

        //TODO: (Optional) set password authentication providers (example set for google and password)
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //setEnabledAuthProvider(AuthProviderType.TWITTER);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        setEnabledAuthProvider(AuthProviderType.PASSWORD);

        showFirebaseLoginPrompt();
        loginRegisterDialog = new LoginRegisterDialog();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        if(LOG_SHOW) Log.i(LOG_TAG, "Logged in using " + authData.getProvider());

        // returned data
        if(LOG_SHOW) Log.i(LOG_TAG, "Login data: " + authData.toString());

        // add user to database
        checkForUser(authData);

        // Check for intent for result (if called by startActivity getCallingActivity is null, called by startActivityForResult is NOT null)
        if (getCallingActivity() != null) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }

    @Override
    public void onFirebaseLoggedOut() {
        if(LOG_SHOW) Log.i(LOG_TAG, "Logged out");
    }

    @Override
    public void onFirebaseLoginProviderError(FirebaseLoginError firebaseError) {
        if(LOG_SHOW) Log.e(LOG_TAG, "Login provider error: " + firebaseError.toString());
        resetFirebaseLoginPrompt();
    }

    @Override
    public void onFirebaseLoginUserError(FirebaseLoginError firebaseError) {
        if(LOG_SHOW) Log.e(LOG_TAG, "Login user error: " + firebaseError.toString());


        Context context = getApplicationContext();
        CharSequence text = firebaseError.message;
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return mRef;
    }

    // Create a new account
    public void createAccount(View view){
        dismissFirebaseLoginPrompt();
        loginRegisterDialog.show(getFragmentManager(),"");
    }

    // Reset Password
    public void resetPassword(View view){
        // Get email account - reset view is inside a linear layout before it shares a parent with email
        View viewParent = (View) view.getParent().getParent();
        EditText emailView = (EditText) viewParent.findViewById(R.id.email);
        if(LOG_SHOW) Log.i(LOG_TAG, "viewParent value: " + String.valueOf(viewParent));

        if (emailView != null) {
            String email = emailView.getText().toString();
            if(LOG_SHOW) Log.i(LOG_TAG, "Email view value: " + email);

            if (!email.equals("")) {
                // request reset email be sent
                mRef.resetPassword(email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Context context = getApplicationContext();
                        CharSequence text = getResources().getString(R.string.reset_email_sent);
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Context context = getApplicationContext();
                        CharSequence text = firebaseError.getMessage();
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
            } else {
                Context context = getApplicationContext();
                CharSequence text = getResources().getString(R.string.reset_email_required);
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }


    // Return to login dialog when back button is pressed
    public void clickLogin(){
        showFirebaseLoginPrompt();
    }

    // Cancel activity - no login
    public void clickCancel(){
        // Check for intent for result (if called by startActivity getCallingActivity is null, called by startActivityForResult is NOT null)
        if (getCallingActivity() != null) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        finish();
    }

    public void checkForUser(final AuthData authData) {
        // Check to see if they exist
        mRef.child("users").child(authData.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DbUserInfo dbUserInfo = dataSnapshot.getValue(DbUserInfo.class);

                if (dbUserInfo == null) {
                    // user does not exist - create user
                    addUserInfo(authData);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // there was an error
                CharSequence text = firebaseError.getMessage();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
        });
    }

    public void addUserInfo(AuthData authData) {
        //TODO: (Optional) Update for any new fields added to DbUserInfo class

        // set user id
        String uid = authData.getUid();

        // create record
        String provider = authData.getProvider();
        String email = getResources().getString(R.string.missing_user_data);
        String profileImageUrl = getResources().getString(R.string.missing_user_data);
        String displayName = getResources().getString(R.string.missing_user_data);

        if(authData.getProviderData().containsKey("email")) email = authData.getProviderData().get("email").toString();
        if(authData.getProviderData().containsKey("profileImageURL")) profileImageUrl = authData.getProviderData().get("profileImageURL").toString();
        if(authData.getProviderData().containsKey("displayName")) displayName = authData.getProviderData().get("displayName").toString();

        DbUserInfo newUserInfo = new DbUserInfo(provider, email, profileImageUrl, displayName);
        mRef.child("users").child(uid).setValue(newUserInfo);
    }
}
