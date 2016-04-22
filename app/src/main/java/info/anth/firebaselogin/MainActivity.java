package info.anth.firebaselogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.client.Firebase;

import info.anth.firebaselogin.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // need to set application context
        Firebase.setAndroidContext(getApplicationContext());
        // setup the Firebase database reference
        mRef = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
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

}
