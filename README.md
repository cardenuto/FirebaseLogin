# Branch - Example-AUID
The Example-AUID branch is the Example-UserInfo branch changed to create a mapping of the standard Firebase UID to an application user ID (AUID). This should give the programmer the ability to change login authentication easily. It will also allow for multiple emails to update the same application user information. The code was also expanded to save user information in shared preferences for synchronous access, and a logout function was added to LoginActivity. 

# FirebaseLogin
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included on the master branch.

# Changes
The changes made in this version of the code updates the base programs for the LoginActivity Process.

### Bug Fixes
- fixed timing issue when the login fully completes and user account data is available
- fixed bug in Register Dialog that when it was canceled it returned the LoginActivity with the cancel intent instead of returning to the login options
- Created progress bar for timing issue with login

### User Information
I created a mapping table that maps the standard Firebase UID to an application user ID (AUID). The AUID is used in place of the UID when adding data specific to the user. The user data had been grouped under a new node userInfo with nodes userMap and users as children. 

![Firebase screen shot](https://github.com/cardenuto/FirebaseLogin/blob/Example-AUID/firebase-user-data-AUID.png)

By using an application user ID, it places the control over the ID in the hands of the application owner. This code provides the ability to group Firebase UIDs into one Application UID and the ability to change authentication methods easily.

### Saving User Information Locally

Version Example-AUID saves some user information locally in Shared Preferences at login. Firebase is an asynchronous data retrieval system (build structure with the data pushed into it using listeners) which for changing data is a nice design. For stagnate data such as user data I wanted the ability to access the data synchronously (having the program wait until the data is retrieved before continuing.) In version Example-AUID I added a class, LocalUserInfo.java, with the UID, AUID, email, display name, and profile image fields stored. (In Firebase you can get some of these fields synchronously by using getAuth, but not all, it is not expandable, and the data cannot be changed should the user want to be called Bob in your system even though their Google Auth name is Fred.) 
