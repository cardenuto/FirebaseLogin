package info.anth.firebaselogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;

import info.anth.firebaselogin.login.LocalUserInfo;
import info.anth.firebaselogin.login.LoginActivity;

public class NewActivity extends AppCompatActivity {

    public static final String LOG_TAG = NewActivity.class.getSimpleName();

    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        // set Firebase reference
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if auth is set if it is not (returns null) start login activity
        if (mRef.getAuth()==null) startActivityForResult(new Intent(this, LoginActivity.class), LoginActivity.RESULT_REQUEST_CODE);

        LocalUserInfo user = new LocalUserInfo(this);
        String message = "Logged In\n";
        message += "UID: " + user.uid;
        message += "\nAUID: " + user.auid;
        message += "\nEmail: " + user.email;
        message += "\nDisplay Name: " + user.displayName;
        message += "\nprofileImageUrl: " + user.profileImageUrl;

        Log.i(LOG_TAG, "onResume from Shared Preferences: " + message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // results returned from login activity
        if (requestCode == LoginActivity.RESULT_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK){
                // TODO (optional) add logic if RESULTS_OK is returned
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // TODO (optional) add additional logic if RESULTS_CANCELED is returned

                // Login was cancelled therefore cancel this activity
                finish();
            }
        }
    }
}
