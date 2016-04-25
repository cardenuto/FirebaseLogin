# Branch - Example-AUID
The Example-AUID branch is the Example-UserInfo branch changed to create a mapping of the standard Firebase UID to an application user ID (AUID). This should give the programmer the ability to change login authentication easily. It will also allow for multiple emails to update the same application user information. The code was also expanded to save user information in shared preferences for synchronous access, and a logout function was added to LoginActivity. 

# FirebaseLogin
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included on the master branch.
