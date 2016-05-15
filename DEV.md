#FirebaseLogin Development

##Current
- update gradle
- change Firebase userInfo path to string for easy changing
- add child path in order to group user information
- fixed bug with local user save, problem with display name and profile image data being swapped (also reordered call)

##Future
- Setting up a presence system
- Save the last login method used, and email address used if Password Auth, and update the login screen
- Check time length of the login – limit need to re-login on android 
- Add Material Design to dialogs
- Check for email already registered with another auth provider
- Ability to change password
- Option not to have user information saved (logging into a second database) - ability to have saved database by database
- add including a unique device install ID

##Presence System
Step 1 would be to define what does presence mean to the application. the code for onDisconnect works ok but it looks at the connection
which stays around a lot longer than the user. It is not until the application is terminated that the connection is broken.

Theory: connection impacts presence but it is not the presence system. If the connection is broken when the application is fully terminated and the termination is
determined on memory allocations and Android's what not then it is not consistent enough to be used.

Tests?
- If I connect to two locations and ref.onDisconect one what happens to the other?
-- maybe onPause disconnect?
- test finish() for application to see if the defined connection is disconnected but it is not

notes: `System.exit(0);` used to exit the application

code:
```java
    public static void setPresence(Firebase reference) {

        // Add current user to presence database
        Firebase presenceRef = reference.child("userInfo/presence");
        final Firebase userRef = presenceRef.push();

        // Add ourselves to presence list when online.
        Firebase connectedRef = reference.child(".info/connected");

        ValueEventListener myConnection = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Remove ourselves when we disconnect.
                userRef.onDisconnect().removeValue();
                userRef.setValue(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("setPresence", "The read failed: " + firebaseError.getMessage());
            }
        };

        connectedRef.addValueEventListener(myConnection);


        // Number of online users is the number of objects in the presence list.
        ValueEventListener myList = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Remove ourselves when we disconnect.
                Log.i("DBCount", "# of online users = " + String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("DBCount", "The read failed: " + firebaseError.getMessage());
            }
        };

        presenceRef.addValueEventListener(myList);

    }
```

## Other Notes
getAUID try to move intent call to function not in activity - don't think this will work

```java
    public String getAUID(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("displayName", "no shared pref");
    }

    public String getAUID(Context context, Boolean withLogin) {

        Firebase reference = new Firebase(getResources().getString(R.string.FIREBASE_BASE_REF));
        String returnMessage;
        Boolean lookupAUID = false;

        if (reference.getAuth() == null) {
            returnMessage = "no login";
            if (withLogin) {
                // call the login intent
                startActivity(new Intent(context, LoginActivity.class));
                if (reference.getAuth() != null) {
                    lookupAUID = true;
                }
            }
        } else {
            lookupAUID = true;
        }

        if (lookupAUID) {
            returnMessage = reference.child("userInfo/userMap").child(reference.getAuth().getUid()).getValue();
            reference.addListenerForSingleValueEvent();
        }

        return "hi";
    }
```
