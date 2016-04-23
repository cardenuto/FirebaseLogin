package info.anth.firebaselogin;

import com.firebase.client.Firebase;

/**
 * Created by A. Cardenuto on 4/23/2016.
 *
 * Used to initialize Firebase Library
 */
public class FirebaseLoginApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
