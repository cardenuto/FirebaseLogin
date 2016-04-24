package info.anth.firebaselogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import info.anth.firebaselogin.login.DbUserInfo;
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

        final TextView loginText = (TextView) findViewById(R.id.login_text);

        mRef.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // User Logged IN
                    final String uid = authData.getUid();

                    // if being created, the AUTH happens prior to adding the values to "users" in the database
                    // need to change this from addListenerForSingleValueEvent to addValueEventListener so we
                    // can capture when the "user" data is updated. Once it is updated remove this listener
                    mRef.child("userInfo/userMap").child(uid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String auid  = (String) dataSnapshot.getValue();

                                mRef.child("userInfo/users").child(auid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        DbUserInfo dbUserInfo = dataSnapshot.getValue(DbUserInfo.class);

                                        if (dbUserInfo != null) {
                                            // user exist
                                            String message = "Logged In\n";
                                            message += "\nProvider: " + dbUserInfo.getProvider();
                                            message += "\nUID: " + uid;
                                            message += "\nEmail: " + dbUserInfo.getEmail();
                                            message += "\nDisplay Name: " + dbUserInfo.getDisplayName();
                                            message += "\nprofileImageUrl: " + dbUserInfo.getProfileImageUrl();

                                            loginText.setText(message);
                                        } else {
                                            loginText.setText("Missing user information.");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(FirebaseError firebaseError) {
                                        // there was an error
                                        loginText.setText(firebaseError.getMessage());
                                    }
                                });

                                // once data is updated remove the listener
                                mRef.child("users").child(uid).removeEventListener(this);

                            } else {
                                loginText.setText("Missing user Mapping information.");
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            // there was an error
                            loginText.setText(firebaseError.getMessage());
                        }
                    });

                } else {
                    // User Logged out
                    String message = "Logged Out";
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
