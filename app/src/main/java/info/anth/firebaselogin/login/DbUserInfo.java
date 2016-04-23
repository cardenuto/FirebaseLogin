// TODO: (Required) Add your package name
package info.anth.firebaselogin.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Primary on 4/23/2016.
 *
 * Firebase User Info data structure
 */

// TODO: (Optional) Adjust the user fields being saved
// need to add to all parts of the DbUserInfo class

@JsonIgnoreProperties(ignoreUnknown = true)
public class DbUserInfo {
    // list the data fields
    private String provider;
    private String email;
    private String profileImageUrl;
    private String displayName;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private DbUserInfo() {
    }

    public DbUserInfo(String provider, String email, String profileImageUrl, String displayName) {
        this.provider = provider;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.displayName = displayName;
    }

    public String getProvider() { return provider; }
    public String getEmail() { return email; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public String getDisplayName() { return displayName; }

    public static class columns {

        //define columns
        public static String COLUMN_PROVIDER = "provider";
        public static String COLUMN_EMAIL = "email";
        public static String COLUMN_PROFILEIMAGEURL = "profileImageUrl";
        public static String COLUMN_DISPLAYNAME = "displayName";

        public static Map<String, Object> getFullMap(DbUserInfo dbUserInfo) {
            Map<String, Object> fullMap = new HashMap<String, Object>();

            fullMap.put(COLUMN_PROVIDER, dbUserInfo.getProvider());
            fullMap.put(COLUMN_EMAIL, dbUserInfo.getEmail());
            fullMap.put(COLUMN_PROFILEIMAGEURL, dbUserInfo.getProfileImageUrl());
            fullMap.put(COLUMN_DISPLAYNAME, dbUserInfo.getDisplayName());

            return fullMap;
        }

        public static DbUserInfo createBlank() {
            return new DbUserInfo("","","","");
        }
    }
}
