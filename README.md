#Branch - Example-UserInfo
This branch is the NewActivity example plus the saving of user data into the database under the users node. It also adds scrolling to the adjusted login screen (fragment_firebase_login.xml) along with a reset password button. This creates the need for a change password button,  this was added to future development. I am not sure it will be part of this project.

# FirebaseLogin
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included on the master branch.

#Changes
The changes made in this version of the code updates the base programs for the LoginActivity Process. 

##User Info
I used a class to define the data structure I wanted added to the Firebase database under the “users” node.  The code creates a child node (under users) using the official UID of the user Firebase creates (i.e.: 15a54f42-aef4-4114-bd98-be624b65d021) and uses the class to define its (the UIDs) child values. (displayName, email, profileImageUrl, and provider) If you wanted to add additional fields you can add them to the class definition and when you assign the values, they will be added to the data. 

<img src="https://github.com/cardenuto/FirebaseLogin/blob/Example-UserInfo/firebase-user-data.png" alt="Firebase data" width="60%">

##Password Reset
Ability for the user to get a temporary password for password authentication was added. This changed the login screen. However, the ability to change the password was not added as mentioned above.

<img src="https://github.com/cardenuto/FirebaseLogin/blob/Example-UserInfo/device-2016-04-23-191257.png" alt="Login Screen" width="40%">

