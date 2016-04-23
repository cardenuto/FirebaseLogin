package info.anth.firebaselogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import info.anth.firebaselogin.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup the Firebase database reference
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
        mRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // User Logged IN
                    String provider = authData.getProvider();
                    String uid = authData.getUid();
                    String email = "Not provided by authData";
                    String profileImageUrl = "Not provided by authData";
                    String displayName = "Not provided by authData";

                    if(authData.getProviderData().containsKey("email")) email = authData.getProviderData().get("email").toString();
                    if(authData.getProviderData().containsKey("profileImageURL")) profileImageUrl = authData.getProviderData().get("profileImageURL").toString();
                    if(authData.getProviderData().containsKey("displayName")) displayName = authData.getProviderData().get("displayName").toString();

                    String message = "Logged In\n";
                    message += "\nProvider: " + provider;
                    message += "\nUID: " + uid;
                    message += "\nEmail: " + email;
                    message += "\nDisplay Name: " + displayName;
                    message += "\nprofileImageUrl: " + profileImageUrl;

                    TextView loginText = (TextView) findViewById(R.id.login_text);
                    loginText.setText(message);

                } else {
                    // User Logged out
                    String message = "Logged Out";
                    TextView loginText = (TextView) findViewById(R.id.login_text);
                    loginText.setText(message);
                }
            }
        });
    }

    public void callLogin (View view) {
        // Logout is logged in
        if (mRef.getAuth() != null) callLogout(view);
        // call the login intent
         startActivity(new Intent(this, LoginActivity.class));
    }

    public void callLogout (View view) {
        // logout
        mRef.unauth();
        if (mRef.getAuth() == null) Log.i(LOG_TAG, "Logout successful");
    }

    public void callNewActivity (View view) {
        startActivity(new Intent(this, NewActivity.class));
    }

}
