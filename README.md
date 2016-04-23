# FirebaseLogin
This repository provides reusable code that can be included in your Android project to add Firebase login functionality. It utilizes the FirebaseUI library (version 0.3.1), encapsulating it into its own activity, to be started when needed. It expands the login functionality by overriding the delivered login layout (fragment_firebase_login.xml) adding multi-language support by implementing resource strings and the ability to create/register a new user.  

It is delivered in the form of examples with setup instructions included. 

Why not delivered as a library? For my own needs I didn’t want these classes and layouts “fixed.” Some of my apps I may want to add different branding, some additional information needs to be collected from the user. Adding this to a project, though not as quick as adding a dependency, is still relatively quick. I am new to Android so if there is a better process please share. 
 
##Structure/UI
This solution adds 5 new files: a login activity (LoginActivity.java), its layout file (activity_login.xml), a dialog to register new users (LoginRegisterDialog.java), its layout file (dialog_login_register.xml), and the overridden dialog used by the FirebaseUI library as the login UI (fragment_firebase_login.xml). 

When the login intent is started the adjusted login UI is displayed. If the user successfully logs in, the activity will close and the user will return to the activity it was called from. 

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211208.png" alt="Step Screen" width="40%">

Should the user dismiss the login screen without logging in, the login activity’s UI will display. 

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211205.png" alt="Step Screen" width="40%">

The cancel button closes the activity, the login button return to the adjusted login UI where the user can create a new account should they wish.

<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211202.png" alt="Step Screen" width="40%">
<img src="https://github.com/cardenuto/FirebaseLogin/blob/master/device-2016-04-22-211151.png" alt="Step Screen" width="40%">

Error checking is performed on the form before the user is able to login. The login button closes the activity returning the user to the prior activity. The cancel button brings the user back to the adjusted login UI. 
