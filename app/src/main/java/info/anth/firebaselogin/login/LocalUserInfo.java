//TODO: (Required) Add your package name
package info.anth.firebaselogin.login;

//TODO: (Required) Add your package name to the resource import
import info.anth.firebaselogin.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Created by Primary on 4/24/2016.
 *
 */
// TODO: (Optional) Change user fields saved locally

public class LocalUserInfo {

    // values
    public String uid;
    public String auid;
    public String email;
    public String displayName;
    public String profileImageUrl;

    public LocalUserInfo(String uid, String auid, String email, String displayName, String profileImageUrl) {
        this.uid = uid;
        this.auid = auid;
        this.email = email;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
    }

    public LocalUserInfo(Activity activity) {
        Resources rw = activity.getResources();
        SharedPreferences sharedPref = activity.getSharedPreferences(rw.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        this.uid = sharedPref.getString(fields.FIELD_UID, null);
        this.auid = sharedPref.getString(fields.FIELD_AUID, null);
        this.email = sharedPref.getString(fields.FIELD_EMAIL, rw.getString(R.string.preference_email_default));
        this.displayName = sharedPref.getString(fields.FIELD_DISPLAY_NAME, rw.getString(R.string.preference_display_name_default));
        this.profileImageUrl = sharedPref.getString(fields.FIELD_PROFILE_IMAGE, rw.getString(R.string.preference_profile_image_default));
    }


    @SuppressLint("CommitPrefEdits")
    public void saveValues(Activity activity) {
        Resources rw = activity.getResources();
        SharedPreferences sharedPref = activity.getSharedPreferences(rw.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(fields.FIELD_UID, this.uid);
        editor.putString(fields.FIELD_AUID, this.auid);
        editor.putString(fields.FIELD_EMAIL, this.email);
        editor.putString(fields.FIELD_DISPLAY_NAME, this.displayName);
        editor.putString(fields.FIELD_PROFILE_IMAGE, this.profileImageUrl);
        editor.commit();
    }

    @SuppressLint("CommitPrefEdits")
    public void clearValues(Activity activity){
        Resources rw = activity.getResources();
        SharedPreferences sharedPref = activity.getSharedPreferences(rw.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // clear out current values
        editor.remove(fields.FIELD_UID);
        editor.remove(fields.FIELD_AUID);
        editor.remove(fields.FIELD_EMAIL);
        editor.remove(fields.FIELD_DISPLAY_NAME);
        editor.remove(fields.FIELD_PROFILE_IMAGE);
        editor.commit();
    }

    public String toString() {
        return "SharedPreferences: " + this.uid + "," + this.auid + "," + this.email + "," + this.displayName + "," + this.profileImageUrl;
    }

    public static class fields {
        public static final String FIELD_UID = "uid";
        public static final String FIELD_AUID = "auid";
        public static final String FIELD_EMAIL = "email";
        public static final String FIELD_DISPLAY_NAME = "displayName";
        public static final String FIELD_PROFILE_IMAGE = "profileImage";
    }
}
